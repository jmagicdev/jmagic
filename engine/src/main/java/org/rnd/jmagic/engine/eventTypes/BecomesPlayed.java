package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BecomesPlayed extends EventType
{	public static final EventType INSTANCE = new BecomesPlayed();

	 private BecomesPlayed()
	{
		super("BECOMES_PLAYED");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject played = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(played.isManaAbility())
		{
			// Refresh the game state here to make sure that, among other
			// things, the actual player has the correct amount of mana in
			// their pool (important for DOUBLE_MANA)
			game.refreshActualState();

			played = played.getActual();
			played.resolve();
		}
		event.setResult(Identity.instance(played));
		return true;
	}
}