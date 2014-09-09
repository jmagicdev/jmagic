package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Catacomb Slug")
@Types({Type.CREATURE})
@SubTypes({SubType.SLUG})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class CatacombSlug extends Card
{
	public CatacombSlug(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(6);
	}
}
