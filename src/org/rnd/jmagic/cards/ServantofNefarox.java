package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Servant of Nefarox")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ServantofNefarox extends Card
{
	public ServantofNefarox(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
