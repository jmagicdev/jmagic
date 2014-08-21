package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SearchLibraryAndPutOnTop extends EventType
{
	public static final EventType INSTANCE = new SearchLibraryAndPutOnTop();

	private SearchLibraryAndPutOnTop()
	{
		super("SEARCH_LIBRARY_AND_PUT_ON_TOP");
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
		Set player = parameters.get(Parameter.PLAYER);

		java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
		searchParameters.put(Parameter.CAUSE, cause);
		searchParameters.put(Parameter.PLAYER, player);
		searchParameters.put(Parameter.NUMBER, ONE);
		searchParameters.put(Parameter.CARD, new Set(player.getOne(Player.class).getLibrary(game.actualState)));
		if(parameters.containsKey(Parameter.TYPE))
			searchParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));
		Event search = createEvent(game, "Search your library for a card.", SEARCH, searchParameters);
		search.perform(event, false);
		Set searchedFor = search.getResult();

		java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
		shuffleParameters.put(Parameter.CAUSE, cause);
		shuffleParameters.put(Parameter.PLAYER, player);
		createEvent(game, "Shuffle your library.", SHUFFLE_LIBRARY, shuffleParameters).perform(event, true);

		GameObject found = searchedFor.getOne(GameObject.class);

		if(found != null)
		{
			Set putOnTop = new Set(game.actualState.<GameObject>get(found.getPhysical().futureSelf));

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.INDEX, ONE);
			moveParameters.put(Parameter.OBJECT, putOnTop);
			createEvent(game, "Put that card on top of your library.", PUT_INTO_LIBRARY, moveParameters).perform(event, true);
		}

		event.setResult(Empty.set);
		return true;
	}
}