package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Progenitus")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR, SubType.HYDRA})
@ManaCost("WWUUBBRRGG")
@Printings({@Printings.Printed(ex = FromTheVaultLegends.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Conflux.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class Progenitus extends Card
{
	public Progenitus(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, SetPattern.EVERYTHING, "everything"));

		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}
