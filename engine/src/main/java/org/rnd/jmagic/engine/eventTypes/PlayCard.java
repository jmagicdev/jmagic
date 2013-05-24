package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PlayCard extends EventType
{	public static final EventType INSTANCE = new PlayCard();

	 private PlayCard()
	{
		super("PLAY_CARD");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

		if(object.getTypes().contains(Type.LAND))
		{
			for(PlayLandAction action: game.createPlayLandActions(player, object))
			{
				java.util.Map<Parameter, Set> landParameters = new java.util.HashMap<Parameter, Set>();
				landParameters.put(Parameter.ACTION, new Set(action));
				landParameters.put(Parameter.PLAYER, new Set(player));
				landParameters.put(Parameter.LAND, new Set(object));
				Event playLand = createEvent(game, player + " plays " + object + ".", PLAY_LAND, landParameters);
				if(playLand.attempt(event))
					return true;
			}
			return false;
		}

		java.util.Map<Parameter, Set> castParameters = new java.util.HashMap<Parameter, Set>();
		castParameters.put(Parameter.PLAYER, new Set(player));
		castParameters.put(Parameter.OBJECT, new Set(object));
		if(parameters.containsKey(Parameter.ALTERNATE_COST))
			castParameters.put(Parameter.ALTERNATE_COST, parameters.get(Parameter.ALTERNATE_COST));
		Event castEvent = createEvent(game, player + " casts " + object + ".", CAST_SPELL_OR_ACTIVATE_ABILITY, castParameters);
		return castEvent.attempt(event);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Identified cause = parameters.get(Parameter.CAUSE).getOne(Identified.class);
		int sourceID = (cause == null ? 0 : cause.ID);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

		if(object.getTypes().contains(Type.LAND))
		{
			// If a PlayLandAction wasn't specified, have the player choose
			// one.
			java.util.Set<PlayLandAction> actions = game.createPlayLandActions(player, object);
			PlayLandAction action = player.sanitizeAndChoose(game.actualState, 1, actions, PlayerInterface.ChoiceType.ACTION, PlayerInterface.ChooseReason.ACTION).get(0);
			boolean ret = action.saveStateAndPerform();

			event.setResult((ret ? Identity.instance(action.played()) : Identity.instance()));
			return ret;
		}

		CastSpellAction cast = new CastSpellAction(game, object, player, sourceID);
		if(parameters.containsKey(Parameter.ALTERNATE_COST))
			cast.forceAlternateCost(Identity.instance(parameters.get(Parameter.ALTERNATE_COST)));

		boolean ret = cast.saveStateAndPerform();
		event.setResult((ret ? Identity.instance(cast.played()) : Identity.instance()));
		return ret;
	}
}