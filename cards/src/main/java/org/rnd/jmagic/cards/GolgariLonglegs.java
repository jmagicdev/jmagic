package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Golgari Longlegs")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3(B/G)(B/G)")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariLonglegs extends Card
{
	public GolgariLonglegs(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
