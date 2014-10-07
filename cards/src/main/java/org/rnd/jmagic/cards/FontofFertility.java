package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Font of Fertility")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class FontofFertility extends Card
{
	public static final class FontofFertilityAbility0 extends ActivatedAbility
	{
		public FontofFertilityAbility0(GameState state)
		{
			super(state, "(1)(G), Sacrifice Font of Fertility: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.addCost(sacrificeThis("Font of Fertility"));
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public FontofFertility(GameState state)
	{
		super(state);

		// (1)(G), Sacrifice Font of Fertility: Search your library for a basic
		// land card, put it onto the battlefield tapped, then shuffle your
		// library.
		this.addAbility(new FontofFertilityAbility0(state));
	}
}
