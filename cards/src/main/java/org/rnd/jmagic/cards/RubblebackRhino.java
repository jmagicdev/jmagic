package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rubbleback Rhino")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class RubblebackRhino extends Card
{
	public RubblebackRhino(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
