package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chrome Steed")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORSE})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ChromeSteed extends Card
{
	public static final class ChromeSteedAbility0 extends StaticAbility
	{
		public ChromeSteedAbility0(GameState state)
		{
			super(state, "Chrome Steed gets +2/+2 as long as you control three or more artifacts.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public ChromeSteed(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Metalcraft \u2014 Chrome Steed gets +2/+2 as long as you control
		// three or more artifacts.
		this.addAbility(new ChromeSteedAbility0(state));
	}
}
