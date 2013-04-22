package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Amphin Cutthroat")
@Types({Type.CREATURE})
@SubTypes({SubType.SALAMANDER, SubType.ROGUE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AmphinCutthroat extends Card
{
	public AmphinCutthroat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);
	}
}
