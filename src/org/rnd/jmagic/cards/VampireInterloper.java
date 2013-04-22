package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vampire Interloper")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.VAMPIRE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VampireInterloper extends Card
{
	public VampireInterloper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Vampire Interloper can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));
	}
}
