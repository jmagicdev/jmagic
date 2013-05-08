package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Voiceless Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class VoicelessSpirit extends Card
{
	public VoicelessSpirit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
