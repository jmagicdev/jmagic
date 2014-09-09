package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rip-Clan Crasher")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("RG")
@ColorIdentity({Color.GREEN, Color.RED})
public final class RipClanCrasher extends Card
{
	public RipClanCrasher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
