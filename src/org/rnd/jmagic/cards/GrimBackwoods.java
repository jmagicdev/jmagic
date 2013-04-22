package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Grim Backwoods")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GrimBackwoods extends Card
{
	public static final class GrimBackwoodsAbility1 extends ActivatedAbility
	{
		public GrimBackwoodsAbility1(GameState state)
		{
			super(state, "(2)(B)(G), (T), Sacrifice a creature: Draw a card.");
			this.setManaCost(new ManaPool("(2)(B)(G)"));
			this.costsTap = true;
			this.addCost(sacrificeACreature());
			this.addEffect(drawACard());
		}
	}

	public GrimBackwoods(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2)(B)(G), (T), Sacrifice a creature: Draw a card.
		this.addAbility(new GrimBackwoodsAbility1(state));
	}
}
