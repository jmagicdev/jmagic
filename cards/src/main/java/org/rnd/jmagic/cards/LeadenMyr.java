package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Leaden Myr")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LeadenMyr extends Card
{
	public LeadenMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
	}
}
