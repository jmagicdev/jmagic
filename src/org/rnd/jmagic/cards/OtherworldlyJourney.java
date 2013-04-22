package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Otherworldly Journey")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class OtherworldlyJourney extends Card
{
	public OtherworldlyJourney(GameState state)
	{
		super(state);

		// Exile target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		EventFactory exile = exile(target, "Exile target creature.");
		this.addEffect(exile);

		SetGenerator thatCard = delayedTriggerContext(NewObjectOf.instance(EffectResult.instance(exile)));

		// At the beginning of the next end step, return that card to the
		// battlefield under its owner's control with a +1/+1 counter on it.
		EventFactory comeBack = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_WITH_COUNTERS, "Return that card to the battlefield under its owner's control with a +1/+1 counter on it");
		comeBack.parameters.put(EventType.Parameter.CAUSE, This.instance());
		comeBack.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(thatCard));
		comeBack.parameters.put(EventType.Parameter.OBJECT, thatCard);
		comeBack.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));

		EventFactory comeBackLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.");
		comeBackLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		comeBackLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		comeBackLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(comeBack));
		this.addEffect(comeBackLater);
	}
}
