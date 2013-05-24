package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Chromatic Sphere")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ChromaticSphere extends Card
{
	public static final class Filter extends org.rnd.jmagic.abilities.TapForMana
	{
		public Filter(GameState state)
		{
			super(state, "(WUBRG)");
			this.setManaCost(new ManaPool("1"));
			this.addCost(sacrificeThis("Chromatic Sphere"));

			this.addEffect(drawACard());

			this.setName("(1), (T), Sacrifice Chromatic Sphere: Add one mana of any color to your mana pool.  Draw a card.");
		}
	}

	public ChromaticSphere(GameState state)
	{
		super(state);

		this.addAbility(new Filter(state));
	}
}
