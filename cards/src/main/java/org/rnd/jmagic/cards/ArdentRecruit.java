package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ardent Recruit")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("W")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ArdentRecruit extends Card
{
	public static final class ArdentRecruitAbility0 extends StaticAbility
	{
		public ArdentRecruitAbility0(GameState state)
		{
			super(state, "Ardent Recruit gets +2/+2 as long as you control three or more artifacts.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public ArdentRecruit(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Metalcraft \u2014 Ardent Recruit gets +2/+2 as long as you control
		// three or more artifacts.
		this.addAbility(new ArdentRecruitAbility0(state));
	}
}
