package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Briber's Purse")
@Types({Type.ARTIFACT})
@ManaCost("X")
@ColorIdentity({})
public final class BribersPurse extends Card
{
	public static final class BribersPurseAbility1 extends ActivatedAbility
	{
		public BribersPurseAbility1(GameState state)
		{
			super(state, "(1), (T), Remove a gem counter from Briber's Purse: Target creature can't attack or block this turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.GEM, ABILITY_SOURCE_OF_THIS, "Remove a gem counter from Briber's Purse"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part1.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), target)));

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), target)));
			this.addEffect(createFloatingEffect("Target creature can't attack or block this turn.", part1, part2));

		}
	}

	public BribersPurse(GameState state)
	{
		super(state);

		// Briber's Purse enters the battlefield with X gem counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X gem counters on it", Counter.CounterType.GEM));

		// (1), (T), Remove a gem counter from Briber's Purse: Target creature
		// can't attack or block this turn.
		this.addAbility(new BribersPurseAbility1(state));
	}
}
