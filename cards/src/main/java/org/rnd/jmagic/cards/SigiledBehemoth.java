package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sigiled Behemoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GW")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SigiledBehemoth extends Card
{
	public SigiledBehemoth(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
