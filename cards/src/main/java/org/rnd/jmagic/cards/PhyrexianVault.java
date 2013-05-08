package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Vault")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.UNCOMMON)})
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
