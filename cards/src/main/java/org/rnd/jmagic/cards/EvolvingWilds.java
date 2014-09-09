package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Evolving Wilds")
@Types({Type.LAND})
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
