package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disaster Radius")
@Types({Type.SORCERY})
@ManaCost("5RR")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class DisasterRadius extends Card
{
	public DisasterRadius(GameState state)
	{
		super(state);

		// As an additional cost to cast Disaster Radius, reveal a creature card
		// from your hand.
		SetGenerator creaturesInHand = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(HandOf.instance(You.instance())));

		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "reveal a creature card from your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, creaturesInHand);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addCost(reveal);

		// Disaster Radius deals X damage to each creature your opponents
		// control, where X is the revealed card's converted mana cost.
		SetGenerator X = ConvertedManaCostOf.instance(CostResult.instance(reveal));
		SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
		this.addEffect(spellDealDamage(X, opponentsCreatures, "Disaster Radius deals X damage to each creature your opponents control, where X is the revealed card's converted mana cost."));
	}
}
