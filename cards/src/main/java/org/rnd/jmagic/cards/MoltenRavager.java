package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Molten Ravager")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MoltenRavager extends Card
{
	public MoltenRavager(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, "Molten Ravager"));
	}
}
