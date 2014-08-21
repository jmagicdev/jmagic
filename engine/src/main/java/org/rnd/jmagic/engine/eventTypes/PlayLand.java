package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PlayLand extends EventType
{
	public static final EventType INSTANCE = new PlayLand();

	private PlayLand()
	{
		super("PLAY_LAND");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.LAND;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Turn currentTurn = game.actualState.currentTurn();

		if(!(player.ID == currentTurn.ownerID))
			return false;

		return parameters.containsKey(Parameter.ACTION) && parameters.get(Parameter.ACTION).getOne(PlayLandAction.class) != null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject land = parameters.get(Parameter.LAND).getOne(GameObject.class);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		if(player.ID != game.actualState.currentTurn().ownerID)
		{
			event.setResult(Empty.set);
			return false;
		}

		PlayLandAction action = parameters.containsKey(EventType.Parameter.ACTION) ? parameters.get(EventType.Parameter.ACTION).getOne(PlayLandAction.class) : null;

		if(action == null)
		{
			event.setResult(Empty.set);
			return false;
		}

		java.util.Map<Parameter, Set> playParameters = new java.util.HashMap<Parameter, Set>();
		playParameters.put(Parameter.CAUSE, new Set(game));
		playParameters.put(Parameter.CONTROLLER, new Set(player));
		playParameters.put(Parameter.OBJECT, new Set(land));
		Event putOntoBattlefield = createEvent(game, player + " puts " + land + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, playParameters);
		putOntoBattlefield.perform(event, true);

		GameObject playedLand = game.actualState.get(putOntoBattlefield.getResult().getOne(ZoneChange.class).newObjectID);

		java.util.Map<Parameter, Set> playFlagParameters = new java.util.HashMap<Parameter, Set>();
		playFlagParameters.put(Parameter.PLAYER, new Set(player));
		playFlagParameters.put(Parameter.OBJECT, new Set(playedLand));
		createEvent(game, player + " plays " + land + ".", BECOMES_PLAYED, playFlagParameters).perform(event, false);

		event.setResult(putOntoBattlefield.getResultGenerator());
		return true;
	}
}