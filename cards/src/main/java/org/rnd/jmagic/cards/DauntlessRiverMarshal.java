package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dauntless River Marshal")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class DauntlessRiverMarshal extends Card
{
	public static final class DauntlessRiverMarshalAbility0 extends StaticAbility
	{
		public DauntlessRiverMarshalAbility0(GameState state)
		{
			super(state, "Dauntless River Marshal gets +1/+1 as long as you control an Island.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));
			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND)));
		}
	}

	public static final class DauntlessRiverMarshalAbility1 extends ActivatedAbility
	{
		public DauntlessRiverMarshalAbility1(GameState state)
		{
			super(state, "(3)(U): Tap target creature.");
			this.setManaCost(new ManaPool("(3)(U)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature"));
		}
	}

	public DauntlessRiverMarshal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Dauntless River Marshal gets +1/+1 as long as you control an Island.
		this.addAbility(new DauntlessRiverMarshalAbility0(state));

		// (3)(U): Tap target creature.
		this.addAbility(new DauntlessRiverMarshalAbility1(state));
	}
}
