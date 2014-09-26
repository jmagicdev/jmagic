package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kheru Bloodsucker")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class KheruBloodsucker extends Card
{
	public static final class KheruBloodsuckerAbility0 extends EventTriggeredAbility
	{
		public KheruBloodsuckerAbility0(GameState state)
		{
			super(state, "Whenever a creature you control with toughness 4 or greater dies, each opponent loses 2 life and you gain 2 life.");

			SetGenerator bigGuys = Intersect.instance(CREATURES_YOU_CONTROL, HasToughness.instance(Between.instance(4, null)));
			this.addPattern(whenXDies(bigGuys));

			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 2, "Each opponent loses 2 life."));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public static final class KheruBloodsuckerAbility1 extends ActivatedAbility
	{
		public KheruBloodsuckerAbility1(GameState state)
		{
			super(state, "(2)(B), Sacrifice another creature: Put a +1/+1 counter on Kheru Bloodsucker.");
			this.setManaCost(new ManaPool("(2)(B)"));

			SetGenerator anotherCreature = RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Kheru Bloodsucker."));
		}
	}

	public KheruBloodsucker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever a creature you control with toughness 4 or greater dies,
		// each opponent loses 2 life and you gain 2 life.
		this.addAbility(new KheruBloodsuckerAbility0(state));

		// (2)(B), Sacrifice another creature: Put a +1/+1 counter on Kheru
		// Bloodsucker.
		this.addAbility(new KheruBloodsuckerAbility1(state));
	}
}
