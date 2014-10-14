package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sagu Mauler")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SaguMauler extends Card
{
	public SaguMauler(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Trample, hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Morph (3)(G)(U) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(G)(U)"));
	}
}
