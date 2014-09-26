package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jor Kadeen, the Prevailer")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("3RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class JorKadeenthePrevailer extends Card
{
	public static final class JorKadeenthePrevailerAbility1 extends StaticAbility
	{
		public JorKadeenthePrevailerAbility1(GameState state)
		{
			super(state, "Metalcraft \u2014 Creatures you control get +3/+0 as long as you control three or more artifacts.");

			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +3, +0));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public JorKadeenthePrevailer(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Metalcraft \u2014 Creatures you control get +3/+0 as long as you
		// control three or more artifacts.
		this.addAbility(new JorKadeenthePrevailerAbility1(state));
	}
}
