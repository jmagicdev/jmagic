package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unmask")
@Types({Type.SORCERY})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Unmask extends Card
{
	public Unmask(GameState state)
	{
		super(state);

		// You may exile a black card from your hand rather than pay Unmask's
		// mana cost.
		SetGenerator blackInHand = Intersect.instance(HasColor.instance(Color.BLACK), InZone.instance(HandOf.instance(You.instance())));
		EventFactory exile = exile(You.instance(), blackInHand, 1, "Exile a black card from your hand");
		CostCollection cost = new CostCollection(CostCollection.TYPE_ALTERNATE, exile);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may exile a black card from your hand rather than pay Unmask's mana cost.", cost));

		// Target player reveals his or her hand.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator inHand = InZone.instance(HandOf.instance(target));
		this.addEffect(reveal(inHand, "Target player reveals his or her hand."));

		// You choose a nonland card from it. That player discards that card.
		EventFactory discard = new EventFactory(EventType.DISCARD_FORCE, "You choose a nonland card from it. That player discards that card.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		discard.parameters.put(EventType.Parameter.TARGET, target);
		discard.parameters.put(EventType.Parameter.CHOICE, RelativeComplement.instance(inHand, HasType.instance(Type.LAND)));
		this.addEffect(discard);
	}
}
