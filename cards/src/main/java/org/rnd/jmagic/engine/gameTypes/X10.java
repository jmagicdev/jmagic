package org.rnd.jmagic.engine.gameTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("X-10")
@Description("You get ten mana per turn cycle.  Basic lands are removed from your library before the game begins.  Library searches are restricted to sixty random cards plus those basic lands.")
public class X10 extends GameType.SimpleGameTypeRule
{
	// if you change this, replace all instances of "sixty" in this file (in
	// text, identifiers, and comments, including this one)
	private static final int SEARCH_LIMIT = 60;

	public static final class MakeMana extends ActivatedAbility
	{
		public MakeMana(GameState state)
		{
			super(state, "Lose an energy counter: Add one mana of any color to your mana pool.");

			EventFactory loseCounter = new EventFactory(EventType.REMOVE_COUNTERS, "Lose an energy counter");
			loseCounter.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			loseCounter.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			loseCounter.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.ENERGY));
			loseCounter.parameters.put(EventType.Parameter.OBJECT, You.instance());
			this.addCost(loseCounter);

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	/**
	 * Takes all the parameters of SEARCH, and restricts the library searches to
	 * sixty random cards plus any basic lands owned by the owner of that
	 * library in the command zone.
	 * 
	 * @eparam RESULT: Sixty randomly chosen cards from each library
	 */
	public static final EventType SEARCH_FOR_SIXTY_RANDOM_CARDS = new EventType("SEARCH_FOR_SIXTY_RANDOM_CARDS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cardParameter = parameters.get(Parameter.CARD);
			Set cards = new Set();
			for(Zone library: cardParameter.getAll(Zone.class))
			{
				if(!library.isLibrary())
				{
					cards.add(library);
					continue;
				}

				Zone commandZone = game.physicalState.commandZone();
				Player owner = library.getOwner(game.physicalState);
				for(GameObject card: commandZone.objects)
				{
					if(card.getSuperTypes().contains(SuperType.BASIC) && card.getTypes().contains(Type.LAND) && card.ownerID == owner.ID)
						cards.add(card);
				}

				if(library.objects.size() <= SEARCH_LIMIT)
				{
					cards.add(library);
					continue;
				}

				java.util.List<GameObject> list = new java.util.ArrayList<GameObject>();
				list.addAll(library.objects);
				java.util.Collections.shuffle(list);
				cards.addAll(list.subList(0, 60));
			}

			parameters.put(EventType.Parameter.CARD, cards);
			Event search = createEvent(game, event.getName(), EventType.SEARCH, parameters);
			boolean ret = search.perform(event, false);
			event.setResult(search.getResultGenerator());

			return ret;
		}
	};

	@Override
	public void modifyGameState(GameState physicalState)
	{
		// Give players ability to make mana
		ContinuousEffect.Part abilityPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
		abilityPart.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(MakeMana.class)));
		abilityPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, Players.instance());

		EventFactory grantAbility = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Each player has \"Lose an energy counter: Add one mana of any color to your mana pool.\"");
		grantAbility.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		grantAbility.parameters.put(EventType.Parameter.EFFECT, Identity.instance(abilityPart));
		grantAbility.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		grantAbility.createEvent(physicalState.game, null).perform(null, true);

		// Start each player with 10 "mana"
		EventFactory startingCounters = new EventFactory(EventType.PUT_COUNTERS, "Each player gets ten energy counters.");
		startingCounters.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		startingCounters.parameters.put(EventType.Parameter.NUMBER, numberGenerator(10));
		startingCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.ENERGY));
		startingCounters.parameters.put(EventType.Parameter.OBJECT, Players.instance());
		startingCounters.createEvent(physicalState.game, null).perform(null, true);

		// Reset each player's "mana" during the untap step
		SetGenerator activePlayer = OwnerOf.instance(CurrentTurn.instance());
		EventFactory loseCounters = new EventFactory(EventType.REMOVE_COUNTERS, "Active player loses all energy counters.");
		loseCounters.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		loseCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.ENERGY));
		loseCounters.parameters.put(EventType.Parameter.OBJECT, activePlayer);

		EventFactory getTenCounters = new EventFactory(EventType.PUT_COUNTERS, "Active player gets ten energy counters");
		getTenCounters.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		getTenCounters.parameters.put(EventType.Parameter.NUMBER, numberGenerator(10));
		getTenCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.ENERGY));
		getTenCounters.parameters.put(EventType.Parameter.OBJECT, activePlayer);

		ContinuousEffect.Part resetCountersEffect = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
		resetCountersEffect.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(sequence(loseCounters, getTenCounters)));

		EventFactory resetCountersEvent = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "During each player's untap step, that player loses all energy counters, then that player gets ten energy counters.");
		resetCountersEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		resetCountersEvent.parameters.put(EventType.Parameter.EFFECT, Identity.instance(resetCountersEffect));
		resetCountersEvent.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		resetCountersEvent.createEvent(physicalState.game, null).perform(null, true);

		// At the start of the game, move all basic lands from each player's
		// library into the command zone face down.
		// Ticket 381 covers a better way to do this than to use replacement
		// effects.
		// After that ticket is finished, fix the kludge in FOR_EACH_PLAYER that
		// supports getting "apnap" order before the first turn of the game.
		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();
		SetGenerator library = LibraryOf.instance(eachPlayer);
		SetGenerator basics = Intersect.instance(InZone.instance(library), HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND));

		EventFactory stripOnePlayersBasics = new EventFactory(EventType.MOVE_OBJECTS, "Put all basic land cards from that player's library into the command zone face down");
		stripOnePlayersBasics.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		stripOnePlayersBasics.parameters.put(EventType.Parameter.TO, CommandZone.instance());
		stripOnePlayersBasics.parameters.put(EventType.Parameter.OBJECT, basics);
		stripOnePlayersBasics.parameters.put(EventType.Parameter.HIDDEN, Empty.instance());

		EventFactory stripEachPlayersBasics = new EventFactory(FOR_EACH_PLAYER, "Put all basic land cards from all libraries into the command zone face down.");
		stripEachPlayersBasics.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		stripEachPlayersBasics.parameters.put(EventType.Parameter.EFFECT, Identity.instance(stripOnePlayersBasics));

		EventPattern startGame = new SimpleEventPattern(EventType.GAME_START);
		EventReplacementEffect stripEffect = new EventReplacementEffect(physicalState.game, "At the start of the game, put all basic land cards from all libraries into the command zone face down.", startGame);
		stripEffect.addEffect(stripEachPlayersBasics);
		stripEffect.addEffect(new EventFactory(EventType.GAME_START, "Start the game."));

		EventFactory stripAtStart = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "At the start of the game, put all basic land cards from all libraries into the command zone face down.");
		stripAtStart.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		stripAtStart.parameters.put(EventType.Parameter.EFFECT, Identity.instance(replacementEffectPart(stripEffect)));
		stripAtStart.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		stripAtStart.parameters.put(EventType.Parameter.USES, numberGenerator(1));
		stripAtStart.createEvent(physicalState.game, null).perform(null, true);

		// Restrict searches to sixty cards plus basics
		// TODO : This interacts with Aven Mindcensor. Is it possible to write
		// this as something other than a replacement effect?
		SimpleEventPattern searchLibrary = new SimpleEventPattern(EventType.SEARCH);
		searchLibrary.put(EventType.Parameter.CARD, LibraryOf.instance(Players.instance()));
		EventReplacementEffect restrictEffect = new EventReplacementEffect(physicalState.game, "If a player would search a library, instead that player searches the basic lands in the command zone owned by the owner of that library and sixty randomly chosen cards from that library.", searchLibrary);

		SetGenerator zonesToSearch = EventParameter.instance(restrictEffect.replacedByThis(), EventType.Parameter.CARD);
		SetGenerator thatLibrary = Intersect.instance(LibraryOf.instance(Players.instance()), zonesToSearch);

		EventFactory chooseSixty = new EventFactory(SEARCH_FOR_SIXTY_RANDOM_CARDS, "Search.");
		chooseSixty.parameters.put(EventType.Parameter.CARD, thatLibrary);
		restrictEffect.addEffect(chooseSixty);

		EventFactory restrictSearches = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "If a player would search a library, instead that player searches the basic lands in the command zone owned by the owner of that library and sixty randomly chosen cards from that library.");
		restrictSearches.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		restrictSearches.parameters.put(EventType.Parameter.EFFECT, Identity.instance(replacementEffectPart(restrictEffect)));
		restrictSearches.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		restrictSearches.createEvent(physicalState.game, null).perform(null, true);
	}
}
