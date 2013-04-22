package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked extends StaticAbility
{
	private final CostCollection kickerCost;
	private final String objectName;

	public EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(GameState state, String objectName, CostCollection kickerCost)
	{
		super(state, objectName + " enters the battlefield with a +1/+1 counter on it for each time it was kicked.");
		this.canApply = NonEmpty.instance();
		this.kickerCost = kickerCost;
		this.objectName = objectName;

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

		EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
		factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
		factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		factory.parameters.put(EventType.Parameter.NUMBER, ThisSpellWasKicked.instance(kickerCost));
		factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
		replacement.addEffect(factory);

		this.addEffectPart(replacementEffectPart(replacement));
	}

	@Override
	public EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked create(Game game)
	{
		return new EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(game.physicalState, this.objectName, this.kickerCost);
	}
}
