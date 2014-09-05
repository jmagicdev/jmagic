package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Phyrexian Vault")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PhyrexianVault extends Card
{
	public static final class SkinBoundBooks extends ActivatedAbility
	{
		public SkinBoundBooks(GameState state)
		{
			super(state, "(2), (T), Sacrifice a creature: Draw a card.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			this.addCost(sacrificeACreature());
			this.addEffect(drawACard());
		}
	}

	public PhyrexianVault(GameState state)
	{
		super(state);

		this.addAbility(new SkinBoundBooks(state));
	}
}
