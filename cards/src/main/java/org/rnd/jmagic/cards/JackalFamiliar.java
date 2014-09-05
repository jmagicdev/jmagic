package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jackal Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class JackalFamiliar extends Card
{
	public static final class NoLoneliness extends StaticAbility
	{
		public NoLoneliness(GameState state)
		{
			super(state, "Jackal Familiar can't attack or block alone.");

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

	public JackalFamiliar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new NoLoneliness(state));
	}
}
