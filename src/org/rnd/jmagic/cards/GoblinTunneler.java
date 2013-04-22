package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Tunneler")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinTunneler extends Card
{
	public static final class GoblinTunnelerAbility0 extends ActivatedAbility
	{
		public GoblinTunnelerAbility0(GameState state)
		{
			super(state, "(T): Target creature with power 2 or less is unblockable this turn.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 2))), "target creature with power 2 or less");

			this.addEffect(createFloatingEffect("Target creature with power 2 or less is unblockable this turn", unblockable(targetedBy(target))));
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
