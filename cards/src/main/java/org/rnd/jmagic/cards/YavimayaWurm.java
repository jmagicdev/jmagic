package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Yavimaya Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class YavimayaWurm extends Card
{
	public YavimayaWurm(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
