package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Induce Despair")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class InduceDespair extends Card
{
	public InduceDespair(GameState state)
	{
		super(state);

		// As an additional cost to cast Induce Despair, reveal a creature card
		// from your hand.
		SetGenerator creaturesInHand = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(HandOf.instance(You.instance())));

		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "reveal a creature card from your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, creaturesInHand);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addCost(reveal);

		// Target creature gets -X/-X until end of turn, where X is the revealed
		// card's converted mana cost.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator X = ConvertedManaCostOf.instance(CostResult.instance(reveal));
		SetGenerator minusX = Subtract.instance(numberGenerator(0), X);
		this.addEffect(ptChangeUntilEndOfTurn(target, minusX, minusX, "Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost."));
	}
}
