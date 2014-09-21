package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Abzan Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("3WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class AbzanGuide extends Card
{
	public AbzanGuide(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Morph (2)(W)(B)(G) (You may cast this card face down as a 2/2
		// creature for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(W)(B)(G)"));
	}
}
