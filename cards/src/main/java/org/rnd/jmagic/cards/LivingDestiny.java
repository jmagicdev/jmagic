package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Living Destiny")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LivingDestiny extends Card
{
	public LivingDestiny(GameState state)
	{
		super(state);

		SetGenerator creaturesInHand = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(HandOf.instance(You.instance())));

		// As an additional cost to cast Living Destiny, reveal a creature card
		// from your hand.
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "reveal a creature card from your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, creaturesInHand);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addCost(reveal);

		// You gain life equal to the revealed card's converted mana cost.
		SetGenerator X = ConvertedManaCostOf.instance(CostResult.instance(reveal));
		this.addEffect(gainLife(You.instance(), X, "You gain life equal to the revealed card's converted mana cost."));
	}
}
