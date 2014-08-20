package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

@Name("House Mulligan Rules")
@Description("All-land and no-land hands can be mulliganed freely.  Each player also gets a free mulligan on a 1 land hand, and on a 1 non-land hand.")
public class HouseMulliganRules extends GameType.SimpleGameTypeRule
{
	public final class HouseMulliganTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		public static final int ONE_LAND_MULLIGAN_USED = 1;
		public static final int ONE_NONLAND_MULLIGAN_USED = 2;

		private java.util.HashMap<Integer, Integer> value = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.value);

		@Override
		@SuppressWarnings("unchecked")
		public HouseMulliganTracker clone()
		{
			HouseMulliganTracker ret = (HouseMulliganTracker)super.clone();
			ret.value = (java.util.HashMap<Integer, Integer>)this.value.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.value);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return (event.type == HOUSE_MULLIGAN);
		}

		@Override
		protected void update(GameState state, Event event)
		{
			int player = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
			int result = event.getResult(state).getOne(Integer.class);

			if(!this.value.containsKey(player))
				this.value.put(player, result);
			else
				this.value.put(player, this.value.get(player) & result);
		}
	}

	/**
	 * @eparam PLAYER: the player mulliganning
	 * @eparam RESULT: an integer flag representing the type of mulligan used (0
	 * for normal)
	 */
	public static final EventType HOUSE_MULLIGAN = new EventType("HOUSE_MULLIGAN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone hand = player.getHand(game.actualState);

			int numLands = 0;
			Set cardsInHand = new Set();
			for(GameObject card: hand)
			{
				if(card.getTypes().contains(Type.LAND))
					++numLands;
				cardsInHand.add(card);
			}

			Integer mulligans = game.actualState.getTracker(HouseMulliganTracker.class).getValue(game.actualState).get(player.ID);
			boolean hasMulliganedForOneLand = false;
			boolean hasMulliganedForOneNonLand = false;

			if(mulligans != null)
			{
				hasMulliganedForOneLand = ((mulligans & HouseMulliganTracker.ONE_LAND_MULLIGAN_USED) == HouseMulliganTracker.ONE_LAND_MULLIGAN_USED);
				hasMulliganedForOneNonLand = ((mulligans & HouseMulliganTracker.ONE_NONLAND_MULLIGAN_USED) == HouseMulliganTracker.ONE_NONLAND_MULLIGAN_USED);
			}

			Set shuffleObjects = Set.fromCollection(cardsInHand);
			shuffleObjects.add(player);

			Zone library = player.getLibrary(game.actualState);
			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.OBJECT, shuffleObjects);
			shuffleParameters.put(Parameter.CAUSE, new Set(game));
			shuffleParameters.put(Parameter.ZONE, new Set(library));

			int result = 0;
			int numberToDraw = hand.objects.size();

			if(numLands == 1)
			{
				if(hasMulliganedForOneLand)
					--numberToDraw;
				else
					result &= HouseMulliganTracker.ONE_LAND_MULLIGAN_USED;
			}
			else if(numLands == cardsInHand.size() - 1)
			{
				if(hasMulliganedForOneNonLand)
					--numberToDraw;
				else
					result &= HouseMulliganTracker.ONE_NONLAND_MULLIGAN_USED;
			}
			else if(numLands != 0 && numLands != cardsInHand.size())
				--numberToDraw;

			java.util.Map<Parameter, Set> drawParameters = new java.util.HashMap<Parameter, Set>();
			drawParameters.put(Parameter.PLAYER, new Set(player));
			drawParameters.put(Parameter.CAUSE, new Set(game));
			drawParameters.put(Parameter.NUMBER, new Set(numberToDraw));

			createEvent(game, "Shuffle " + cardsInHand + " into " + library + ".", SHUFFLE_INTO_LIBRARY, shuffleParameters).perform(event, true);
			createEvent(game, player + " draws " + numberToDraw + " cards.", DRAW_CARDS, drawParameters).perform(event, true);

			event.setResult(org.rnd.jmagic.engine.generators.Identity.instance(result));
			return true;
		}
	};

	@Override
	public void modifyGameState(GameState physicalState)
	{
		EventReplacementEffect replacement = new EventReplacementEffect(physicalState.game, "Use house mulligan rules instead of normal ones.", new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.MULLIGAN));
		replacement.addEffect(new EventFactory(HOUSE_MULLIGAN, "Use house mulligan rules instead."));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, org.rnd.jmagic.engine.generators.Identity.instance(replacement));

		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Use house mulligan rules instead of normal ones.");
		factory.parameters.put(EventType.Parameter.CAUSE, org.rnd.jmagic.engine.generators.CurrentGame.instance());
		factory.parameters.put(EventType.Parameter.EFFECT, org.rnd.jmagic.engine.generators.Identity.instance(part));
		factory.parameters.put(EventType.Parameter.EXPIRES, org.rnd.jmagic.engine.generators.Identity.instance(org.rnd.jmagic.engine.generators.Empty.instance()));
		factory.createEvent(physicalState.game, null).perform(null, true);
	}
}
