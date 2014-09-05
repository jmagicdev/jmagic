package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Makindi Shieldmate")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER, SubType.ALLY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MakindiShieldmate extends Card
{
	public MakindiShieldmate(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
