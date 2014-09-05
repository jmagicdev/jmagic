package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Midnight Duelist")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MidnightDuelist extends Card
{
	public MidnightDuelist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Protection from Vampires
		SetGenerator vampires = HasSubType.instance(SubType.VAMPIRE);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, vampires, "vampires"));
	}
}
