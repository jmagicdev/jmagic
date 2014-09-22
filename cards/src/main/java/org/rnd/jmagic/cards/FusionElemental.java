package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fusion Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("WUBRG")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.RED, Color.GREEN})
public final class FusionElemental extends Card
{
	public FusionElemental(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);
	}
}
