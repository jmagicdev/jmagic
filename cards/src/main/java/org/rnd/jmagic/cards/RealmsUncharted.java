package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Realms Uncharted")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class RealmsUncharted extends Card
{
	public static final PlayerInterface.ChooseReason FIRST_REASON = new PlayerInterface.ChooseReason("RealmsUncharted", "Choose an opponent.", true);
	public static final PlayerInterface.ChooseReason SECOND_REASON = new PlayerInterface.ChooseReason("RealmsUncharted", "Choose two cards to put into the graveyard.", true);

	/**
	 * Search your library for four [cards] with different names.
	 * 
	 * @eparam PLAYER: who's searching
	 * @eparam TYPE: what kind of things to search for
	 * @eparam RESULT: found cards
	 */
	public static final EventType SEARCH_FOR_CARDS_WITH_DIFFERENT_NAMES = new EventType("SEARCH_FOR_CARDS_WITH_DIFFERENT_NAMES")
	{

		@Override
		public Parameter affects()
		{
			return null;
		}

		private boolean legal(Set searchedFor)
		{
			java.util.Set<String> names = new java.util.HashSet<String>();
			for(GameObject o: searchedFor.getAll(GameObject.class))
			{
				if(names.contains(o.getName()))
					return false;
				names.add(o.getName());
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set searchedFor;
			do
			{
				java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
				searchParameters.put(EventType.Parameter.CAUSE, new Set(event.getSource()));
				searchParameters.put(EventType.Parameter.PLAYER, new Set(you));
				searchParameters.put(EventType.Parameter.NUMBER, new Set(4));
				searchParameters.put(EventType.Parameter.CARD, new Set(you.getLibrary(game.actualState)));
				searchParameters.put(EventType.Parameter.TYPE, parameters.get(Parameter.TYPE));
				Event search = createEvent(game, "Search your library for four land cards with different names", EventType.SEARCH, searchParameters);
				search.perform(event, false);

				searchedFor = search.getResult();
			}
			while(!legal(searchedFor));

			event.setResult(searchedFor);
			return true;
		}

	};

	public RealmsUncharted(GameState state)
	{
		super(state);

		// Search your library for four land cards with different names
		EventFactory search = new EventFactory(SEARCH_FOR_CARDS_WITH_DIFFERENT_NAMES, "Search your library for four land cards with different names");
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));
		this.addEffect(search);

		SetGenerator thoseCards = EffectResult.instance(search);

		// and reveal them.
		EventFactory reveal = new EventFactory(EventType.REVEAL, "and reveal them.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, thoseCards);
		this.addEffect(reveal);

		EventFactory chooseOpponent = new EventFactory(EventType.PLAYER_CHOOSE, "An opponent");
		chooseOpponent.parameters.put(EventType.Parameter.PLAYER, You.instance());
		chooseOpponent.parameters.put(EventType.Parameter.CHOICE, OpponentsOf.instance(You.instance()));
		chooseOpponent.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.PLAYER, FIRST_REASON));
		this.addEffect(chooseOpponent);

		SetGenerator anOpponent = EffectResult.instance(chooseOpponent);

		EventFactory chooseTwo = new EventFactory(EventType.PLAYER_CHOOSE, "chooses two of those cards.");
		chooseTwo.parameters.put(EventType.Parameter.PLAYER, anOpponent);
		chooseTwo.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		chooseTwo.parameters.put(EventType.Parameter.CHOICE, thoseCards);
		chooseTwo.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, SECOND_REASON));
		this.addEffect(chooseTwo);

		SetGenerator chosenCards = EffectResult.instance(chooseTwo);
		SetGenerator theRest = RelativeComplement.instance(thoseCards, chosenCards);

		// Put the chosen cards into your graveyard and the rest into your hand.
		EventFactory toGraveyard = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Put the chosen cards into your graveyard");
		toGraveyard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		toGraveyard.parameters.put(EventType.Parameter.OBJECT, chosenCards);

		EventFactory toHand = new EventFactory(EventType.MOVE_OBJECTS, "and the rest into your hand.");
		toHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		toHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		toHand.parameters.put(EventType.Parameter.OBJECT, theRest);

		this.addEffect(simultaneous(toGraveyard, toHand));

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
