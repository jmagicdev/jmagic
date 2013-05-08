package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mogg Flunkies")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MoggFlunkies extends Card
{
	public static final class MoggFlunkiesAbility0 extends StaticAbility
	{
		public MoggFlunkiesAbility0(GameState state)
		{
			super(state, "Mogg Flunkies can't attack or block alone.");

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

	public MoggFlunkies(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Mogg Flunkies can't attack or block alone.
		this.addAbility(new MoggFlunkiesAbility0(state));
	}
}
