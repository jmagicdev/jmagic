package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghalma's Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.SOLDIER})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GhalmasWarden extends Card
{
	public static final class GhalmasWardenAbility0 extends StaticAbility
	{
		public GhalmasWardenAbility0(GameState state)
		{
			super(state, "Ghalma's Warden gets +2/+2 as long as you control three or more artifacts.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public GhalmasWarden(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Metalcraft \u2014 Ghalma's Warden gets +2/+2 as long as you control
		// three or more artifacts.
		this.addAbility(new GhalmasWardenAbility0(state));
	}
}
