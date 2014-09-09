package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sunblade Elf")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WARRIOR})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SunbladeElf extends Card
{
	public static final class SunbladeElfAbility0 extends StaticAbility
	{
		public SunbladeElfAbility0(GameState state)
		{
			super(state, "Sunblade Elf gets +1/+1 as long as you control a Plains.");

			// Wild Nacatl gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Plains.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator plains = HasSubType.instance(SubType.PLAINS);
			SetGenerator youControlAPlains = Intersect.instance(youControl, plains);
			this.canApply = Both.instance(youControlAPlains, this.canApply);
		}
	}

	public static final class SunbladeElfAbility1 extends ActivatedAbility
	{
		public SunbladeElfAbility1(GameState state)
		{
			super(state, "(4)(W): Creatures you control get +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(4)(W)"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));
		}
	}

	public SunbladeElf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sunblade Elf gets +1/+1 as long as you control a Plains.
		this.addAbility(new SunbladeElfAbility0(state));

		// (4)(W): Creatures you control get +1/+1 until end of turn.
		this.addAbility(new SunbladeElfAbility1(state));
	}
}
