package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class VampireKill extends EventTriggeredAbility
{
	private String thisName;

	public VampireKill(GameState state, String thisName)
	{
		super(state, "Whenever a creature dealt damage by " + thisName + " this turn dies, put a +1/+1 counter on " + thisName + ".");

		this.thisName = thisName;

		SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
		SetGenerator damagedByThis = DealtDamageByThisTurn.instance(thisCreature);

		this.addPattern(whenXDies(damagedByThis));

		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thisCreature, "Put a +1/+1 counter on Sengir Vampire."));

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
	}

	@Override
	public VampireKill create(Game game)
	{
		return new VampireKill(game.physicalState, this.thisName);
	}
}
