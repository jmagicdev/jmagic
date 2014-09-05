package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Amphin Cutthroat")
@Types({Type.CREATURE})
@SubTypes({SubType.SALAMANDER, SubType.ROGUE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
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
