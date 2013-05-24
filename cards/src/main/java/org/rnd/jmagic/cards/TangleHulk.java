package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tangle Hulk")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.BEAST})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TangleHulk extends Card
{
	public TangleHulk(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// (2)(G): Regenerate Tangle Hulk.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(2)(G)", this.getName()));
	}
}
