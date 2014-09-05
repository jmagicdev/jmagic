package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fusion Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("WUBRG")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class FusionElemental extends Card
{
	public FusionElemental(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);
	}
}
