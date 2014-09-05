package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dross Ripper")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HOUND})
@ManaCost("4")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DrossRipper extends Card
{
	public static final class DrossRipperAbility0 extends ActivatedAbility
	{
		public DrossRipperAbility0(GameState state)
		{
			super(state, "(2)(B): Dross Ripper gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Dross Ripper gets +1/+1 until end of turn."));
		}
	}

	public DrossRipper(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2)(B): Dross Ripper gets +1/+1 until end of turn.
		this.addAbility(new DrossRipperAbility0(state));
	}
}
