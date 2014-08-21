package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SearchForAllAndPutInto extends EventType
{
	public static final EventType INSTANCE = new SearchForAllAndPutInto();

	private SearchForAllAndPutInto()
	{
		super("SEARCH_FOR_ALL_AND_PUT_INTO");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		java.util.Set<Zone> zones = parameters.get(Parameter.ZONE).getAll(Zone.class);
		Set cards = new Set();
		for(Zone zone: zones)
			cards.addAll(zone.objects);

		SetGenerator restriction = parameters.get(Parameter.TYPE).getOne(SetGenerator.class);
		Set canBeFound = restriction.evaluate(game, event.getSource());

		// All cards in public zones must be chosen.
		// 400.2. ... Graveyard, battlefield, stack, exile, ante, and
		// command are public zones. ...
		Set toPut = new Set();
		for(GameObject card: cards.getAll(GameObject.class))
		{
			Zone zone = card.getZone();
			if(zone.isGraveyard() //
					|| game.actualState.stack().equals(zone) //
					|| game.actualState.battlefield().equals(zone) //
					|| game.actualState.exileZone().equals(zone) //
					|| game.actualState.commandZone().equals(zone)) //
			{
				if(canBeFound.contains(card))
					toPut.add(card);
				zones.remove(zone);
			}
		}

		// Cards in other zones don't need to be found, even if the entire
		// zone is revealed:
		// 400.2. ... Library and hand are hidden zones, even if all the
		// cards in one such zone happen to be revealed.
		java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
		searchParameters.put(Parameter.CAUSE, new Set(cause));
		searchParameters.put(Parameter.PLAYER, new Set(player));
		searchParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, null)));
		searchParameters.put(Parameter.CARD, Set.fromCollection(zones));
		searchParameters.put(Parameter.TYPE, new Set(restriction));
		Event search = createEvent(game, player + " searches " + zones, EventType.SEARCH, searchParameters);
		search.perform(event, false);

		toPut.addAll(search.getResult().getAll(GameObject.class));

		Set result = new Set();
		Zone to = parameters.get(Parameter.TO).getOne(Zone.class);
		if(!toPut.isEmpty())
		{
			java.util.Map<EventType.Parameter, Set> moveParameters = new java.util.HashMap<EventType.Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, new Set(cause));
			moveParameters.put(Parameter.OBJECT, toPut);
			moveParameters.put(Parameter.TO, new Set(to));
			Event move = createEvent(game, "Put " + toPut + " into " + to, EventType.MOVE_OBJECTS, moveParameters);
			move.perform(event, false);
			result.addAll(move.getResult());
		}

		event.setResult(Identity.fromCollection(result));
		return true;
	}
}