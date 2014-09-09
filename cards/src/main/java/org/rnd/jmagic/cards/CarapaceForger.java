package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Carapace Forger")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ARTIFICER})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class CarapaceForger extends Card
{
	public static final class CarapaceForgerAbility0 extends StaticAbility
	{
		public CarapaceForgerAbility0(GameState state)
		{
			super(state, "Carapace Forger gets +2/+2 as long as you control three or more artifacts.");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));
			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public CarapaceForger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Metalcraft \u2014 Carapace Forger gets +2/+2 as long as you control
		// three or more artifacts.
		this.addAbility(new CarapaceForgerAbility0(state));
	}
}
