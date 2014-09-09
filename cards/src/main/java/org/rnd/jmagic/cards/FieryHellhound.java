package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fiery Hellhound")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.HOUND})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class FieryHellhound extends Card
{
	public FieryHellhound(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R): Fiery Hellhound gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
