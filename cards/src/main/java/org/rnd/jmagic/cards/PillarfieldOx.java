package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pillarfield Ox")
@Types({Type.CREATURE})
@SubTypes({SubType.OX})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class PillarfieldOx extends Card
{
	public PillarfieldOx(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);
	}
}
