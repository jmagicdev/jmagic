package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Reckless Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RecklessBrute extends Card
{
	public RecklessBrute(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Reckless Brute attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, "Reckless Brute"));
	}
}
