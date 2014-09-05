package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Flameborn Hellion")
@Types({Type.CREATURE})
@SubTypes({SubType.HELLION})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FlamebornHellion extends Card
{
	public FlamebornHellion(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Flameborn Hellion attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
