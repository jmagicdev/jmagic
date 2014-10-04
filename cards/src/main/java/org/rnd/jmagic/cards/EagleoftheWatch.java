package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Eagle of the Watch")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class EagleoftheWatch extends Card
{
	public EagleoftheWatch(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
