package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Tunneler")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GoblinTunneler extends Card
{
	public static final class GoblinTunnelerAbility0 extends ActivatedAbility
	{
		public GoblinTunnelerAbility0(GameState state)
		{
			super(state, "(T): Target creature with power 2 or less can't be blocked this turn.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 2))), "target creature with power 2 or less");

			this.addEffect(createFloatingEffect("Target creature with power 2 or less can't be blocked this turn", unblockable(targetedBy(target))));
		}
	}

	public GoblinTunneler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target creature with power 2 or less is unblockable this turn.
		this.addAbility(new GoblinTunnelerAbility0(state));
	}
}
