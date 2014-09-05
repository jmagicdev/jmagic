package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Havengul Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class HavengulVampire extends Card
{
	public static final class HavengulVampireAbility1 extends EventTriggeredAbility
	{
		public HavengulVampireAbility1(GameState state)
		{
			super(state, "Whenever another creature dies, put a +1/+1 counter on Havengul Vampire.");
			this.addPattern(whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefield());

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Havengul Vampire."));
		}
	}

	public HavengulVampire(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Havengul Vampire deals combat damage to a player, put a
		// +1/+1 counter on it.
		this.addAbility(new org.rnd.jmagic.abilities.MeleeGetPlusOnePlusOneCounters(state, "Havengul Vampire", 1));

		// Whenever another creature dies, put a +1/+1 counter on Havengul
		// Vampire.
		this.addAbility(new HavengulVampireAbility1(state));
	}
}
