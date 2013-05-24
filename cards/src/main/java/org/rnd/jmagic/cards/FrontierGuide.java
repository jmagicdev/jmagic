package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Frontier Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class FrontierGuide extends Card
{
	public static final class TourGuide extends ActivatedAbility
	{
		public TourGuide(GameState state)
		{
			super(state, "(3)(G), (T): Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.");

			this.setManaCost(new ManaPool("(3)(G)"));

			this.costsTap = true;

			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public FrontierGuide(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (3)(G), (T): Search your library for a basic land card and put it
		// onto the battlefield tapped. Then shuffle your library.
		this.addAbility(new TourGuide(state));
	}
}
