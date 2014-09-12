package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blood Host")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class BloodHost extends Card
{
	public static final class BloodHostAbility0 extends ActivatedAbility
	{
		public BloodHostAbility0(GameState state)
		{
			super(state, "(1)(B), Sacrifice another creature: Put a +1/+1 counter on Blood Host and you gain 2 life.");
			this.setManaCost(new ManaPool("(1)(B)"));

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Blood Host"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public BloodHost(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (1)(B), Sacrifice another creature: Put a +1/+1 counter on Blood Host
		// and you gain 2 life.
		this.addAbility(new BloodHostAbility0(state));
	}
}
