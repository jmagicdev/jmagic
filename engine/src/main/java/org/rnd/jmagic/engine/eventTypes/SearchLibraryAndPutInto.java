package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SearchLibraryAndPutInto extends EventType
{
	public static final EventType INSTANCE = new SearchLibraryAndPutInto();

	private SearchLibraryAndPutInto()
	{
		super("SEARCH_LIBRARY_AND_PUT_INTO");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Player target = player;
		if(parameters.containsKey(Parameter.TARGET))
			target = parameters.get(Parameter.TARGET).getOne(Player.class);

		org.rnd.util.NumberRange num = getRange(parameters.get(Parameter.NUMBER));
		Zone library = target.getLibrary(game.actualState);

		java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
		searchParameters.put(Parameter.CAUSE, cause);
		searchParameters.put(Parameter.PLAYER, new Set(player));
		searchParameters.put(Parameter.NUMBER, new Set(num));
		searchParameters.put(Parameter.CARD, new Set(library));
		if(parameters.containsKey(Parameter.TYPE))
			searchParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));

		int lower = num.getLower(0);
		int upper = num.getUpper(library.objects.size());
		String searchName;
		if(lower == upper)
			searchName = "Search your library for " + lower + " cards";
		else
			searchName = "Search your library for " + lower + " to " + upper + " cards";
		Event searchEvent = createEvent(game, searchName, EventType.SEARCH, searchParameters);
		searchEvent.perform(event, true);

		java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
		shuffleParameters.put(Parameter.CAUSE, cause);
		shuffleParameters.put(Parameter.PLAYER, new Set(target));

		target = target.getActual();

		Set object = searchEvent.getResult();
		Set to = parameters.get(Parameter.TO);
		boolean ret;
		if(to.getOne(Zone.class).equals(game.actualState.battlefield()) && parameters.containsKey(Parameter.TAPPED))
		{
			java.util.Map<Parameter, Set> ontoBattlefieldTappedParameters = new java.util.HashMap<Parameter, Set>();
			ontoBattlefieldTappedParameters.put(Parameter.CAUSE, cause);
			ontoBattlefieldTappedParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			ontoBattlefieldTappedParameters.put(Parameter.OBJECT, object);
			Event putOntoBattlefieldTapped = createEvent(game, "Put " + object + " onto the battlefield tapped", EventType.PUT_ONTO_BATTLEFIELD_TAPPED, ontoBattlefieldTappedParameters);

			ret = putOntoBattlefieldTapped.perform(event, true);
			event.setResult(NewObjectOf.instance(putOntoBattlefieldTapped.getResultGenerator()).evaluate(game, null));
		}
		else
		{
			ret = true;
			Set result = new Set();
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, to);
			if(parameters.containsKey(Parameter.CONTROLLER))
				moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			if(parameters.containsKey(Parameter.INDEX))
				moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
			if(parameters.containsKey(Parameter.HIDDEN))
				moveParameters.put(Parameter.HIDDEN, parameters.get(Parameter.HIDDEN));
			moveParameters.put(Parameter.OBJECT, object);

			Event moveEvent = createEvent(game, "Put the object into", EventType.MOVE_OBJECTS, moveParameters);
			ret = moveEvent.perform(event, true) && ret;
			result.addAll(NewObjectOf.instance(moveEvent.getResultGenerator()).evaluate(game, null));

			event.setResult(result);
		}

		Event shuffleEvent = createEvent(game, target + " shuffles their library", EventType.SHUFFLE_LIBRARY, shuffleParameters);
		shuffleEvent.perform(event, true);

		return ret;
	}
}