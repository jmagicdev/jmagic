package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Guardians of Akrasa")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class GuardiansofAkrasa extends Card
{
	public GuardiansofAkrasa(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
