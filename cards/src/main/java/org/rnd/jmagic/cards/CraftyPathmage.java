package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crafty Pathmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class CraftyPathmage extends Card
{
	public static final class Cloak extends ActivatedAbility
	{
		public Cloak(GameState state)
		{
			super(state, "(T): Target creature with power 2 or less can't be blocked this turn.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 2))), "target creature with power 2 or less");

			this.addEffect(createFloatingEffect("Target creature with power 2 or less can't be blocked this turn", unblockable(targetedBy(target))));
		}
	}

	public CraftyPathmage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Cloak(state));
	}
}
