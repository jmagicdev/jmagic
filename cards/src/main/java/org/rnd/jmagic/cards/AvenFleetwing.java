package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Aven Fleetwing")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AvenFleetwing extends Card
{
	public AvenFleetwing(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
