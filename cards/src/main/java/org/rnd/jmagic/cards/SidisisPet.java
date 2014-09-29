package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sidisi's Pet")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.APE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class SidisisPet extends Card
{
	public SidisisPet(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Morph (1)(B) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(1)(B)"));
	}
}
