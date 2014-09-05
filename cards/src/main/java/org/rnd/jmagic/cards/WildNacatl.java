package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wild Nacatl")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CAT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WildNacatl extends Card
{
	public static final class MountainPump extends StaticAbility
	{
		public MountainPump(GameState state)
		{
			super(state, "Wild Nacatl gets +1/+1 as long as you control a Mountain.");

			// Wild Nacatl gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Mountain.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator mountains = HasSubType.instance(SubType.MOUNTAIN);
			SetGenerator youControlAMountain = Intersect.instance(youControl, mountains);
			this.canApply = Both.instance(youControlAMountain, this.canApply);
		}
	}

	public static final class PlainsPump extends StaticAbility
	{
		public PlainsPump(GameState state)
		{
			super(state, "Wild Nacatl gets +1/+1 as long as you control a Plains.");

			// Wild Nacatl gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Plains.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator plains = HasSubType.instance(SubType.PLAINS);
			SetGenerator youControlAPlains = Intersect.instance(youControl, plains);
			this.canApply = Both.instance(youControlAPlains, this.canApply);
		}
	}

	public WildNacatl(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new MountainPump(state));
		this.addAbility(new PlainsPump(state));
	}
}
