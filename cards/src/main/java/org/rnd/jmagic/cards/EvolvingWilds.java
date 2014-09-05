package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Evolving Wilds")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class EvolvingWilds extends Card
{
	public static final class Terraform extends ActivatedAbility
	{
		public Terraform(GameState state)
		{
			super(state, "(T), Sacrifice Evolving Wilds: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.");

			this.costsTap = true;

			this.addCost(sacrificeThis("Evolving Wilds"));

			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public EvolvingWilds(GameState state)
	{
		super(state);

		this.addAbility(new Terraform(state));
	}
}
