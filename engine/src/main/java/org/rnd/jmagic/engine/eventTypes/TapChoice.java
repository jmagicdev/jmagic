package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class TapChoice extends EventType
{
	public static final EventType INSTANCE = new TapChoice();

	private TapChoice()
	{
		super("TAP_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set choices = parameters.get(Parameter.CHOICE);
		int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

		int tappable = 0;
		for(GameObject choice: choices.getAll(GameObject.class))
			if(!choice.isTapped())
			{
				tappable++;
				if(tappable >= required)
					return true;
			}

		return false;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		java.util.Set<GameObject> tappable = parameters.get(Parameter.CHOICE).getAll(GameObject.class);
		java.util.Set<GameObject> toRemove = new java.util.HashSet<GameObject>();
		for(GameObject o: tappable)
			if(o.isTapped())
				toRemove.add(o);
		tappable.removeAll(toRemove);

		org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));
		PlayerInterface.ChooseReason reason = PlayerInterface.ChooseReason.TAP;
		if(parameters.containsKey(Parameter.REASON))
			reason = parameters.get(Parameter.REASON).getOne(PlayerInterface.ChooseReason.class);
		
		if(range.getLower(0) > tappable.size())
			event.allChoicesMade = false;
		java.util.List<GameObject> chosen = player.sanitizeAndChoose(game.actualState, range.getLower(0), range.getUpper(tappable.size()), tappable, PlayerInterface.ChoiceType.OBJECTS, reason);

		event.putChoices(player, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean ret = event.allChoicesMade;
		Set objects = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

		java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
		tapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		tapParameters.put(Parameter.OBJECT, objects);
		Event tap = createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, tapParameters);
		ret = tap.perform(event, false) && ret;

		event.setResult(tap.getResultGenerator());
		return ret;
	}

}