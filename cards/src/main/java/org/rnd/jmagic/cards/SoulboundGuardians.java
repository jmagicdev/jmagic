package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Soulbound Guardians")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SPIRIT})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class SoulboundGuardians extends Card
{
	public SoulboundGuardians(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
