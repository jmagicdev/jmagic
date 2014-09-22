package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Druid of the Anima")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1G")
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class DruidoftheAnima extends Card
{
	public DruidoftheAnima(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RGW)"));
	}
}
