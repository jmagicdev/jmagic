package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SacrificeChoice extends EventType
{
	public static final EventType INSTANCE = new SacrificeChoice();

	private SacrificeChoice()
	{
		super("SACRIFICE_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);
		if(required == 0)
			return true;

		java.util.Map<Player, Set> validChoices = this.validChoices(game, event, parameters);
		for(Player p: parameters.get(Parameter.PLAYER).getAll(Player.class))
			if(!validChoices.containsKey(p) || (validChoices.get(p).size() < required))
				return false;

		return true;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.util.NumberRange numberToSac = getRange(parameters.get(Parameter.NUMBER));

		java.util.Map<Player, Set> validChoices = this.validChoices(game, event, parameters);
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			if(!validChoices.containsKey(player))
			{
				event.allChoicesMade = false;
				continue;
			}
			java.util.Set<GameObject> thisPlayersStuff = validChoices.get(player).getAll(GameObject.class);
			if(thisPlayersStuff.size() < numberToSac.getLower(0))
				event.allChoicesMade = false;

			int size = thisPlayersStuff.size();
			java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, numberToSac.getLower(0), numberToSac.getUpper(size), thisPlayersStuff, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SACRIFICE);
			event.putChoices(player, choices);
		}
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Set result = new Set();
		boolean allSacrificed = event.allChoicesMade;

		// get the player out of the parameters and figure out what they
		// control
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Set choices = event.getChoices(player);

			java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
			sacParameters.put(Parameter.CAUSE, cause);
			sacParameters.put(Parameter.PLAYER, new Set(player));
			sacParameters.put(Parameter.PERMANENT, choices);

			Event sacrifice = createEvent(game, player + " sacrifices " + choices + ".", SACRIFICE_PERMANENTS, sacParameters);
			if(!sacrifice.perform(event, false))
				allSacrificed = false;
			result.addAll(sacrifice.getResult());
		}
		event.setResult(Identity.fromCollection(result));
		return allSacrificed;
	}

	private java.util.Map<Player, Set> validChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Set players = parameters.get(Parameter.PLAYER);
		java.util.Map<Player, Set> ret = new java.util.HashMap<Player, Set>();
		for(GameObject o: parameters.get(Parameter.CHOICE).getAll(GameObject.class))
		{
			Player controller = o.getController(game.actualState);
			if(!players.contains(controller))
				continue;

			java.util.Map<Parameter, Set> attemptParameters = new java.util.HashMap<Parameter, Set>();
			attemptParameters.put(Parameter.CAUSE, cause);
			attemptParameters.put(Parameter.PLAYER, new Set(controller));
			attemptParameters.put(Parameter.PERMANENT, new Set(o));
			Event toAttempt = createEvent(game, "Sacrifice " + o, EventType.SACRIFICE_ONE_PERMANENT, attemptParameters);
			if(toAttempt.attempt(event))
			{
				if(ret.containsKey(controller))
					ret.get(controller).add(o);
				else
					ret.put(controller, new Set(o));
			}
		}
		return ret;
	}
}