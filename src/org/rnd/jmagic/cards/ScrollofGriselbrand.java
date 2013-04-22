package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scroll of Griselbrand")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ScrollofGriselbrand extends Card
{
	public static final class ScrollofGriselbrandAbility0 extends ActivatedAbility
	{
		public ScrollofGriselbrandAbility0(GameState state)
		{
			super(state, "(1), Sacrifice Scroll of Griselbrand: Target opponent discards a card. If you control a Demon, that player loses 3 life.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Scroll of Griselbrand"));

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator youControlADemon = Intersect.instance(HasSubType.instance(SubType.DEMON), ControlledBy.instance(You.instance()));
			this.addEffect(discardCards(target, 1, "Target opponent discards a card."));
			this.addEffect(ifThen(youControlADemon, loseLife(target, 3, "That player loses 3 life."), "If you control a Demon, that player loses 3 life."));
		}
	}

	public ScrollofGriselbrand(GameState state)
	{
		super(state);

		// (1), Sacrifice Scroll of Griselbrand: Target opponent discards a
		// card. If you control a Demon, that player loses 3 life.
		this.addAbility(new ScrollofGriselbrandAbility0(state));
	}
}
