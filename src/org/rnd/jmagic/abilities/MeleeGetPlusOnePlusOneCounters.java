package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class MeleeGetPlusOnePlusOneCounters extends EventTriggeredAbility
{
	private String thisName;
	private int N;

	public MeleeGetPlusOnePlusOneCounters(GameState state, String thisName, int number)
	{
		super(state, "Whenever " + thisName + " deals combat damage to a player, put " + org.rnd.util.NumberNames.get(number, "a") + " +1/+1 counter" + (number == 1 ? "" : "s") + " on it.");

		this.thisName = thisName;
		this.N = number;

		this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));
		EventFactory counterFactory = putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thisName);
		this.addEffect(counterFactory);
	}

	@Override
	public MeleeGetPlusOnePlusOneCounters create(Game game)
	{
		return new MeleeGetPlusOnePlusOneCounters(game.physicalState, this.thisName, this.N);
	}
}
