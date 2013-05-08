package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EnchantedCardComesBackToHand extends EventTriggeredAbility
{
	private final String cardDescription;

	/**
	 * When enchanted permanent dies, return that card to its owner's hand.
	 * 
	 * @param state The game state in which to create this ability
	 * @param cardDescription Something like "enchanted land"
	 */
	public EnchantedCardComesBackToHand(GameState state, String cardDescription)
	{
		super(state, "When " + cardDescription + " dies, return that card to its owner's hand.");
		this.cardDescription = cardDescription;

		this.addPattern(whenXDies(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

		SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
		SetGenerator owner = OwnerOf.instance(thatCard);

		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return that card to its owner's hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(owner));
		move.parameters.put(EventType.Parameter.OBJECT, thatCard);
		this.addEffect(move);
	}

	@Override
	public EnchantedCardComesBackToHand create(Game game)
	{
		return new EnchantedCardComesBackToHand(game.physicalState, this.cardDescription);
	}
}