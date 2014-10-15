package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bassara Tower Archer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER})
@ManaCost("GG")
@ColorIdentity({Color.GREEN})
public final class BassaraTowerArcher extends Card
{
	public BassaraTowerArcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Hexproof, reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
