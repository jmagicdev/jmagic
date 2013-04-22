package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Catacomb Slug")
@Types({Type.CREATURE})
@SubTypes({SubType.SLUG})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
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
