package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Druid of the Anima")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
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
