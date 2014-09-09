package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Wayfinder")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.MERFOLK})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class MerfolkWayfinder extends Card
{
	/**
	 * @eparam CAUSE: wayfinder's trigger
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam OBJECT: top three cards of PLAYER's library
	 * @eparam RESULT: empty
	 */
	public static final EventType MERFOLK_WAYFINDER_EVENT = new EventType("MERFOLK_WAYFINDER_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set topThree = parameters.get(Parameter.OBJECT);

			Set islands = new Set();
			Set others = new Set();
			for(GameObject o: topThree.getAll(GameObject.class))
				if(o.getSubTypes().contains(SubType.ISLAND))
					islands.add(o);
				else
					others.add(o);

			Set cause = parameters.get(Parameter.CAUSE);
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set hand = new Set(you.getHand(game.actualState));

			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, cause);
			handParameters.put(Parameter.TO, hand);
			handParameters.put(Parameter.OBJECT, islands);
			Event handMove = createEvent(game, "Put all Island cards revealed this way into your hand", MOVE_OBJECTS, handParameters);

			java.util.Map<Parameter, Set> bottomParameters = new java.util.HashMap<Parameter, Set>();
			bottomParameters.put(Parameter.CAUSE, cause);
			bottomParameters.put(Parameter.INDEX, new Set(-1));
			bottomParameters.put(Parameter.OBJECT, others);
			Event bottomMove = createEvent(game, "Put all Island cards revealed this way into your hand", PUT_INTO_LIBRARY, bottomParameters);

			handMove.perform(event, false);
			bottomMove.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}

	};

	public static final class FindIslands extends EventTriggeredAbility
	{
		public FindIslands(GameState state)
		{
			super(state, "When Merfolk Wayfinder enters the battlefield, reveal the top three cards of your library. Put all Island cards revealed this way into your hand and the rest on the bottom of your library in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator topThree = TopCards.instance(3, LibraryOf.instance(You.instance()));
			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top three cards of your library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topThree);
			this.addEffect(reveal);

			EventFactory move = new EventFactory(MERFOLK_WAYFINDER_EVENT, "Put all Island cards revealed this way into your hand and the rest on the bottom of your library in any order.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.PLAYER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, topThree);
			this.addEffect(move);
		}
	}

	public MerfolkWayfinder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Merfolk Wayfinder enters the battlefield, reveal the top three
		// cards of your library. Put all Island cards revealed this way into
		// your hand and the rest on the bottom of your library in any order.
		this.addAbility(new FindIslands(state));
	}
}
