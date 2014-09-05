package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Servant of Nefarox")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
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
