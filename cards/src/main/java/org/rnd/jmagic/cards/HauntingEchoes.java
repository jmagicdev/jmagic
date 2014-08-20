package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Haunting Echoes")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HauntingEchoes extends Card
{
	/**
	 * @eparam CAUSE: The Haunting Echoes card
	 * @eparam CONTROLLER: the player searching
	 * @eparam PLAYER: the target
	 * @eparam RESULT: empty
	 */
	public static final EventType HAUNTING_ECHOES_EVENT = new EventType("HAUNTING_ECHOES_EVENT")
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
			Player searcher = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			Zone graveyard = player.getGraveyard(game.actualState);
			Set cardsToRemove = RelativeComplement.get(Set.fromCollection(graveyard.objects), Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND)).evaluate(game.actualState, null));

			java.util.Map<Parameter, Set> exileParameters = new java.util.HashMap<Parameter, Set>();
			exileParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			exileParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
			exileParameters.put(Parameter.OBJECT, cardsToRemove);
			Event exileEvent = createEvent(game, "Exile all cards from target player's graveyard other than basic land cards.", EventType.MOVE_OBJECTS, exileParameters);
			boolean ret = exileEvent.perform(event, true);

			SetGenerator cardsExiledThisWay = NewObjectOf.instance(exileEvent.getResultGenerator());
			SetGenerator cardsWithSameNames = HasName.instance(NameOf.instance(cardsExiledThisWay));

			player = player.getActual();

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			searchParameters.put(Parameter.PLAYER, new Set(searcher));
			searchParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, null)));
			searchParameters.put(Parameter.CARD, new Set(player.getLibrary(game.actualState)));
			searchParameters.put(Parameter.TYPE, new Set(cardsWithSameNames));
			Event searchEvent = createEvent(game, "For each card exiled this way, search that player's library for all cards with the same name as that card.", EventType.SEARCH, searchParameters);
			ret = searchEvent.perform(event, true);

			Set cardsToRemove2 = searchEvent.getResult();
			player = player.getActual();

			java.util.Map<Parameter, Set> exileParameters2 = new java.util.HashMap<Parameter, Set>();
			exileParameters2.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			exileParameters2.put(Parameter.TO, new Set(game.actualState.exileZone()));
			exileParameters2.put(Parameter.OBJECT, cardsToRemove2);
			Event exileEvent2 = createEvent(game, "Exile them.", EventType.MOVE_OBJECTS, exileParameters2);
			ret = exileEvent2.perform(event, true);

			player = player.getActual();

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			shuffleParameters.put(Parameter.PLAYER, new Set(player));
			Event shuffleEvent = createEvent(game, "Then that player shuffles his or her library.", EventType.SHUFFLE_LIBRARY, shuffleParameters);
			ret = shuffleEvent.perform(event, true);

			event.setResult(Empty.set);

			return ret;
		}
	};

	public HauntingEchoes(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		this.addEffect(new EventFactory(HAUNTING_ECHOES_EVENT, parameters, "Exile all cards from target player's graveyard other than basic land cards. For each card exiled this way, search that player's library for all cards with the same name as that card and exile them. Then that player shuffles his or her library."));
	}
}
