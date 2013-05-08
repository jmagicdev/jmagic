package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sakura-Tribe Elder")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.SNAKE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SakuraTribeElder extends Card
{
	public static final class STESac extends ActivatedAbility
	{
		public STESac(GameState state)
		{
			super(state, "Sacrifice Sakura-Tribe Elder: Search your library for a basic land card, put that card onto the battlefield tapped, then shuffle your library.");

			this.addCost(sacrificeThis("Sakura-Tribe Elder"));

			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public SakuraTribeElder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new STESac(state));
	}
}
