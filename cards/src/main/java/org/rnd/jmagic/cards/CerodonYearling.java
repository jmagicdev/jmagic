package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cerodon Yearling")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class CerodonYearling extends Card
{
	public CerodonYearling(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
