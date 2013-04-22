package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gavony Ironwright")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GavonyIronwright extends Card
{
	public static final class GavonyIronwrightAbility0 extends StaticAbility
	{
		public GavonyIronwrightAbility0(GameState state)
		{
			super(state, "As long as you have 5 or less life, other creatures you control get +1/+4.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), +1, +4));

			this.canApply = Both.instance(this.canApply, FatefulHour.instance());
		}
	}

	public GavonyIronwright(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Fateful hour \u2014 As long as you have 5 or less life, other
		// creatures you control get +1/+4.
		this.addAbility(new GavonyIronwrightAbility0(state));
	}
}
