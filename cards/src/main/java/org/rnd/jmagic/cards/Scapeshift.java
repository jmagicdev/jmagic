package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scapeshift")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Scapeshift extends Card
{
	/**
	 * @eparam CAUSE: scapeshift
	 * @eparam PLAYER: you
	 * @eparam RESULT: empty
	 */
	public static final EventType SCAPESHIFT_EVENT = new EventType("SCAPESHIFT_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
			sacParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			sacParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, null)));
			sacParameters.put(Parameter.CHOICE, HasType.instance(Type.LAND).evaluate(game, null));
			sacParameters.put(Parameter.PLAYER, new Set(player));
			Event sacEvent = createEvent(game, "Sacrifice any number of lands.", EventType.SACRIFICE_CHOICE, sacParameters);
			sacEvent.perform(event, true);

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			searchParameters.put(Parameter.CONTROLLER, new Set(player));
			searchParameters.put(Parameter.PLAYER, new Set(player));
			searchParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, sacEvent.getResult().size())));
			searchParameters.put(Parameter.TO, new Set(game.actualState.battlefield()));
			searchParameters.put(Parameter.TAPPED, Empty.set);
			searchParameters.put(Parameter.TYPE, new Set(HasType.instance(Type.LAND)));
			Event searchEvent = createEvent(game, "Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.", EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters);
			searchEvent.perform(event, true);

			event.setResult(Empty.set);

			return true;
		}
	};

	public Scapeshift(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(SCAPESHIFT_EVENT, "Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
