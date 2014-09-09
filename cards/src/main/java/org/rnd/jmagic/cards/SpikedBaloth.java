package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spiked Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class SpikedBaloth extends Card
{
	public SpikedBaloth(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
