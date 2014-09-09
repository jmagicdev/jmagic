package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Razorfield Rhino")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.RHINO})
@ManaCost("6")
@ColorIdentity({})
public final class RazorfieldRhino extends Card
{
	public static final class RazorfieldRhinoAbility0 extends StaticAbility
	{
		public RazorfieldRhinoAbility0(GameState state)
		{
			super(state, "Razorfield Rhino gets +2/+2 as long as you control three or more artifacts.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public RazorfieldRhino(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Metalcraft \u2014 Razorfield Rhino gets +2/+2 as long as you control
		// three or more artifacts.
		this.addAbility(new RazorfieldRhinoAbility0(state));
	}
}
