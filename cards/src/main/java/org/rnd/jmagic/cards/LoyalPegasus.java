package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Loyal Pegasus")
@Types({Type.CREATURE})
@SubTypes({SubType.PEGASUS})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class LoyalPegasus extends Card
{
	public static final class LoyalPegasusAbility1 extends StaticAbility
	{
		public LoyalPegasusAbility1(GameState state)
		{
			super(state, "Loyal Pegasus can't attack or block alone.");

			SetGenerator thisIsAttacking = Intersect.instance(This.instance(), Attacking.instance());
			SetGenerator oneAttacker = Intersect.instance(numberGenerator(1), Count.instance(Attacking.instance()));
			SetGenerator attackingAlone = Both.instance(thisIsAttacking, oneAttacker);
			ContinuousEffect.Part attacking = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			attacking.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackingAlone));
			this.addEffectPart(attacking);

			SetGenerator thisIsBlocking = Intersect.instance(This.instance(), Blocking.instance());
			SetGenerator oneBlocker = Intersect.instance(numberGenerator(1), Count.instance(Blocking.instance()));
			SetGenerator blockingAlone = Both.instance(thisIsBlocking, oneBlocker);
			ContinuousEffect.Part blocking = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			blocking.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingAlone));
			this.addEffectPart(blocking);
		}
	}

	public LoyalPegasus(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Loyal Pegasus can't attack or block alone.
		this.addAbility(new LoyalPegasusAbility1(state));
	}
}
