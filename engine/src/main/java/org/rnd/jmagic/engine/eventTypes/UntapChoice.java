package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class UntapChoice extends EventType
{	public static final EventType INSTANCE = new UntapChoice();

	 private UntapChoice()
	{
		super("UNTAP_CHOICE");
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

		org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));
		int required = range.getLower(0);

		int untappable = 0;
		for(GameObject choice: choices.getAll(GameObject.class))
			if(choice.isTapped())
			{
				untappable++;
				if(untappable >= required)
					return true;
			}

		return false;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		java.util.Set<GameObject> untappable = parameters.get(Parameter.CHOICE).getAll(GameObject.class);
		java.util.Set<GameObject> toRemove = new java.util.HashSet<GameObject>();
		for(GameObject o: untappable)
			if(!o.isTapped())
				toRemove.add(o);
		untappable.removeAll(toRemove);

		org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));

		if(range.getLower(0) > untappable.size())
			event.allChoicesMade = false;
		java.util.List<GameObject> chosen = player.sanitizeAndChoose(game.actualState, range.getLower(0), range.getUpper(untappable.size()), untappable, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.UNTAP);

		event.putChoices(player, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean ret = event.allChoicesMade;
		Set objects = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

		java.util.Map<Parameter, Set> untapParameters = new java.util.HashMap<Parameter, Set>();
		untapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		untapParameters.put(Parameter.OBJECT, objects);
		Event untap = createEvent(game, "Untap " + objects, EventType.UNTAP_PERMANENTS, untapParameters);
		ret = untap.perform(event, false) && ret;

		event.setResult(untap.getResultGenerator());
		return ret;
	}
}