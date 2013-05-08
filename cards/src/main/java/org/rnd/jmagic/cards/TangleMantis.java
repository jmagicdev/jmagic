package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tangle Mantis")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TangleMantis extends Card
{
	public TangleMantis(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
