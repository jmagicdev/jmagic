package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BeginTurn extends EventType
{	public static final EventType INSTANCE = new BeginTurn();

	 private BeginTurn()
	{
		super("BEGIN_TURN");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Turn turn = parameters.get(Parameter.TURN).getOne(Turn.class);
		game.physicalState.setCurrentTurn(turn);
		event.setResult(Identity.instance(turn));
		return true;
	}
}