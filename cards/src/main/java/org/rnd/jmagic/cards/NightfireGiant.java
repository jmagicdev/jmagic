package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightfire Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.GIANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class NightfireGiant extends Card
{
	public static final class NightfireGiantAbility0 extends StaticAbility
	{
		public NightfireGiantAbility0(GameState state)
		{
			super(state, "Nightfire Giant gets +1/+1 as long as you control a Mountain.");

			// Wild Nacatl gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Mountain.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator mountains = HasSubType.instance(SubType.MOUNTAIN);
			SetGenerator youControlAMountain = Intersect.instance(youControl, mountains);
			this.canApply = Both.instance(youControlAMountain, this.canApply);
		}
	}

	public static final class NightfireGiantAbility1 extends ActivatedAbility
	{
		public NightfireGiantAbility1(GameState state)
		{
			super(state, "(4)(R): Nightfire Giant deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(4)(R)"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Nightfire Giant deals 2 damage to target creature or player."));
		}
	}

	public NightfireGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Nightfire Giant gets +1/+1 as long as you control a Mountain.
		this.addAbility(new NightfireGiantAbility0(state));

		// (4)(R): Nightfire Giant deals 2 damage to target creature or player.
		this.addAbility(new NightfireGiantAbility1(state));
	}
}
