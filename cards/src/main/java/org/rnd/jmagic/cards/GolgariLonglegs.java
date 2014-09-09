package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golgari Longlegs")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3(B/G)(B/G)")
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
