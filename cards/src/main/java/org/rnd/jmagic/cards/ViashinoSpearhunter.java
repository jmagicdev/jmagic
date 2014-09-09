package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Viashino Spearhunter")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VIASHINO})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class ViashinoSpearhunter extends Card
{
	public ViashinoSpearhunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
