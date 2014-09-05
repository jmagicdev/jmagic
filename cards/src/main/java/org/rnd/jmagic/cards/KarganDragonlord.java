package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

// imported to make the leveler line that adds three abilities not 238490284 characters long.
import org.rnd.jmagic.abilities.keywords.*;

@Name("Kargan Dragonlord")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class KarganDragonlord extends Card
{
	public KarganDragonlord(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Level up (R)
		this.addAbility(new LevelUp(state, "(R)"));

		// LEVEL 4-7
		// 4/4
		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 7, 4, 4, "Flying", Flying.class));

		// LEVEL 8+
		// 8/8
		// Flying, trample
		// (R): Kargan Dragonlord gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 8, 8, 8, "Flying, trample; (R): Kargan Dragonlord gets +1/+0 until end of turn.", Flying.class, Trample.class, org.rnd.jmagic.abilities.Firebreathing.class));
	}
}
