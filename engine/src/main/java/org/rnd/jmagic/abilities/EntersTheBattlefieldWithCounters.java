package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EntersTheBattlefieldWithCounters extends StaticAbility
{
	private final String objectName;
	private final SetGenerator N;
	private final String counterString;
	private final Counter.CounterType type;

	public EntersTheBattlefieldWithCounters(GameState state, String objectName, int N, Counter.CounterType type)
	{
		super(state, objectName + " enters the battlefield with " + format(N, type) + " on it.");

		this.objectName = objectName;
		this.N = numberGenerator(N);
		// counterString includes the type of counter and the words "on it" to
		// facilitate things like "enters the battlefield with a number of
		// charge counters on it equal to the number of Mountains you control".
		this.counterString = org.rnd.util.NumberNames.get(N) + " " + type + (N > 1 ? "s" : "") + " on it.";
		this.type = type;

		generateEffect();
	}

	/**
	 * @param state Game state in which to construct the ability.
	 * @param objectName The name of the object that has this ability.
	 * @param N How many counters the object will enter the battlefield with.
	 * @param counterString Description of the number and type of counters which
	 * includes the phrase "on it". For example, Golgari Grave-Troll's text here
	 * is "a +1/+1 counter on it for each creature card in your graveyard".
	 * @param type The type of counter.
	 */
	public EntersTheBattlefieldWithCounters(GameState state, String objectName, SetGenerator N, String counterString, Counter.CounterType type)
	{
		super(state, objectName + " enters the battlefield with " + counterString + ".");

		this.objectName = objectName;
		this.N = N;
		this.counterString = counterString;
		this.type = type;

		generateEffect();
	}

	private static String format(int N, Counter.CounterType type)
	{
		String one = "a";
		if(type.toString().startsWith("a") || type.toString().startsWith("e") || type.toString().startsWith("i") || type.toString().startsWith("o") || type.toString().startsWith("u"))
			one = "an";
		return org.rnd.util.NumberNames.get(N, one) + " " + type + (N > 1 ? "s" : "");
	}

	private void generateEffect()
	{
		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

		EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
		factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
		factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(this.type));
		factory.parameters.put(EventType.Parameter.NUMBER, this.N);
		factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
		replacement.addEffect(factory);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}

	@Override
	public EntersTheBattlefieldWithCounters create(Game game)
	{
		return new EntersTheBattlefieldWithCounters(game.physicalState, this.objectName, this.N, this.counterString, this.type);
	}
}
