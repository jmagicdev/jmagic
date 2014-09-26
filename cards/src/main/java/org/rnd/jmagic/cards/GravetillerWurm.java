package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gravetiller Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class GravetillerWurm extends Card
{
	public static final class GravetillerWurmAbility1 extends StaticAbility
	{
		public GravetillerWurmAbility1(GameState state)
		{
			super(state, "Morbid \u2014 Gravetiller Wurm enters the battlefield with four +1/+1 counters on it if a creature died this turn.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = Morbid.instance();

			state.ensureTracker(new Morbid.Tracker());
		}
	}

	public GravetillerWurm(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Morbid \u2014 Gravetiller Wurm enters the battlefield with four +1/+1
		// counters on it if a creature died this turn.
		this.addAbility(new GravetillerWurmAbility1(state));
	}
}
