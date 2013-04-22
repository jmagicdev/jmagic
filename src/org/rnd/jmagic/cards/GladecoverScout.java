package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gladecover Scout")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GladecoverScout extends Card
{
	public GladecoverScout(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
