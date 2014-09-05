package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Terramorphic Expanse")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TerramorphicExpanse extends Card
{
	public static final class Terraform extends ActivatedAbility
	{
		public Terraform(GameState state)
		{
			super(state, "(T), Sacrifice Terramorphic Expanse: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.");

			this.costsTap = true;

			this.addCost(sacrificeThis("Terramorphic Expanse"));

			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public TerramorphicExpanse(GameState state)
	{
		super(state);

		this.addAbility(new Terraform(state));
	}
}
