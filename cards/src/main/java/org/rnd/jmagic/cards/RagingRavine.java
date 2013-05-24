package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Raging Ravine")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class RagingRavine extends Card
{
	public static final class Pump extends EventTriggeredAbility
	{
		public Pump(GameState state)
		{
			super(state, "Whenever this creature attacks, put a +1/+1 counter on it.");
			this.addPattern(whenThisAttacks());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "this creature"));
		}
	}

	public static final class Animate extends ActivatedAbility
	{
		public Animate(GameState state)
		{
			super(state, "(2)(R)(G): Until end of turn, Raging Ravine becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land.");

			this.setManaCost(new ManaPool("2RG"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 3, 3);
			animator.addColor(Color.RED);
			animator.addColor(Color.GREEN);
			animator.addSubType(SubType.ELEMENTAL);
			animator.addAbility(Pump.class);
			this.addEffect(createFloatingEffect("Until end of turn, Raging Ravine becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land.", animator.getParts()));
		}
	}

	public RagingRavine(GameState state)
	{
		super(state);

		// Raging Ravine enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (R) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RG)"));

		// (2)(R)(G): Until end of turn, Raging Ravine becomes a 3/3 red and
		// green Elemental creature with
		// "Whenever this creature attacks, put a +1/+1 counter on it." It's
		// still a land.
		this.addAbility(new Animate(state));
	}
}
