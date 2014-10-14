package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wildfire Cerberus")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class WildfireCerberus extends Card
{
	public static final class WildfireCerberusAbility1 extends EventTriggeredAbility
	{
		public WildfireCerberusAbility1(GameState state)
		{
			super(state, "When Wildfire Cerberus becomes monstrous, it deals 2 damage to each opponent and each creature your opponents control.");
			this.addPattern(whenThisBecomesMonstrous());

			SetGenerator opponents = OpponentsOf.instance(You.instance());
			SetGenerator enemyCreatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(opponents));
			this.addEffect(permanentDealDamage(2, Union.instance(opponents, enemyCreatures), "Wildfire Cerberus deals 2 damage to each opponent and each creature your opponents control."));
		}
	}

	public WildfireCerberus(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// (5)(R)(R): Monstrosity 1. (If this creature isn't monstrous, put a
		// +1/+1 counter on it and it becomes monstrous.)
		this.addAbility(new org.rnd.jmagic.abilities.Monstrosity(state, "(5)(R)(R)", 1));

		// When Wildfire Cerberus becomes monstrous, it deals 2 damage to each
		// opponent and each creature your opponents control.
		this.addAbility(new WildfireCerberusAbility1(state));
	}
}
