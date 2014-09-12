package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cruel Sadist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ASSASSIN})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class CruelSadist extends Card
{
	public static final class CruelSadistAbility0 extends ActivatedAbility
	{
		public CruelSadistAbility0(GameState state)
		{
			super(state, "(B), (T), Pay 1 life: Put a +1/+1 counter on Cruel Sadist.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 1, "Pay 1 life"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Cruel Sadist"));
		}
	}

	public static final class CruelSadistAbility1 extends ActivatedAbility
	{
		public CruelSadistAbility1(GameState state)
		{
			super(state, "(2)(B), (T), Remove X +1/+1 counters from Cruel Sadist: Cruel Sadist deals X damage to target creature.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.costsTap = true;

			SetGenerator X = ValueOfX.instance(This.instance());
			EventFactory removeCounters = removeCountersFromThis(X, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Remove X +1/+1 counters from Cruel Sadist");
			removeCounters.usesX();
			this.addCost(removeCounters);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(X, target, "Cruel Sadist deals X damage to target creature."));
		}
	}

	public CruelSadist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B), (T), Pay 1 life: Put a +1/+1 counter on Cruel Sadist.
		this.addAbility(new CruelSadistAbility0(state));

		// (2)(B), (T), Remove X +1/+1 counters from Cruel Sadist: Cruel Sadist
		// deals X damage to target creature.
		this.addAbility(new CruelSadistAbility1(state));
	}
}
