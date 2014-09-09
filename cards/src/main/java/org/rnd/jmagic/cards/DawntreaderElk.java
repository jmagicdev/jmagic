package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Dawntreader Elk")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class DawntreaderElk extends Card
{
	public static final class DawntreaderElkAbility0 extends ActivatedAbility
	{
		public DawntreaderElkAbility0(GameState state)
		{
			super(state, "(G), Sacrifice Dawntreader Elk: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.");
			this.setManaCost(new ManaPool("(G)"));
			this.addCost(sacrificeThis("Dawntreader Elk"));
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public DawntreaderElk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G), Sacrifice Dawntreader Elk: Search your library for a basic land
		// card, put it onto the battlefield tapped, then shuffle your library.
		this.addAbility(new DawntreaderElkAbility0(state));
	}
}
