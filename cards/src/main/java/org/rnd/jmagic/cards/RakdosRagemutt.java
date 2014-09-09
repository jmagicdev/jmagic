package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Ragemutt")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND, SubType.ELEMENTAL})
@ManaCost("3BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosRagemutt extends Card
{
	public RakdosRagemutt(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Lifelink, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
