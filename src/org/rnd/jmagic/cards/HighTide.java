package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("High Tide")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.FALLEN_EMPIRES, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HighTide extends Card
{
	public HighTide(GameState state)
	{
		super(state);

		EventPattern triggerOnMana = tappedForMana(Players.instance(), landPermanents(SubType.ISLAND));

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
		parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("U")));
		parameters.put(EventType.Parameter.PLAYER, EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER));
		Set manaEvent = new Set(new EventFactory(EventType.ADD_MANA, parameters, "That player adds (U) to his or her mana pool."));

		EventType.ParameterMap triggerParameters = new EventType.ParameterMap();
		triggerParameters.put(EventType.Parameter.CAUSE, This.instance());
		triggerParameters.put(EventType.Parameter.EVENT, Identity.instance(triggerOnMana));
		triggerParameters.put(EventType.Parameter.EFFECT, Identity.instance(manaEvent));
		triggerParameters.put(EventType.Parameter.EXPIRES, Identity.instance(Intersect.instance(CurrentStep.instance(), CleanupStepOf.instance(Players.instance()))));
		this.addEffect(new EventFactory(EventType.CREATE_DELAYED_TRIGGER, triggerParameters, "Until end of turn, whenever a player taps an Island for mana, that player adds (U) to his or her mana pool."));
	}
}
