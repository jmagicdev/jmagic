package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lumengrid Gargoyle")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GARGOYLE})
@ManaCost("6")
@ColorIdentity({})
public final class LumengridGargoyle extends Card
{
	public LumengridGargoyle(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
