package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mulch")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Mulch extends Card
{
	/**
	 * @eparam CAUSE: Mulch
	 * @eparam PLAYER: CAUSE's controller
	 * @eparam OBJECT: top four cards of PLAYER's library
	 */
	public static final EventType MULCH_EVENT = new EventType("MULCH_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set topFour = parameters.get(Parameter.OBJECT);
			Set lands = new Set();
			for(GameObject o: topFour.getAll(GameObject.class))
				if(o.getTypes().contains(Type.LAND))
					lands.add(o);

			Set mulch = parameters.get(Parameter.CAUSE);
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, mulch);
			handParameters.put(Parameter.TO, new Set(you.getHand(game.actualState)));
			handParameters.put(Parameter.OBJECT, lands);
			Event moveToHand = createEvent(game, "Put all land cards revealed this way into your hand", MOVE_OBJECTS, handParameters);
			moveToHand.perform(event, false);

			you = you.getActual();
			topFour.removeAll(lands);
			java.util.Map<Parameter, Set> graveyardParameters = new java.util.HashMap<Parameter, Set>();
			graveyardParameters.put(Parameter.CAUSE, mulch);
			graveyardParameters.put(Parameter.TO, new Set(you.getGraveyard(game.actualState)));
			graveyardParameters.put(Parameter.OBJECT, topFour);
			Event moveToGraveyard = createEvent(game, "Put all other cards revealed this way into your graveyard", MOVE_OBJECTS, graveyardParameters);
			moveToGraveyard.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public Mulch(GameState state)
	{
		super(state);

		SetGenerator topFour = TopCards.instance(4, LibraryOf.instance(You.instance()));
		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top four cards of your library.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, topFour);
		this.addEffect(reveal);

		EventFactory moveCards = new EventFactory(MULCH_EVENT, "Put all land cards revealed this way into your hand and the rest into your graveyard.");
		moveCards.parameters.put(EventType.Parameter.CAUSE, This.instance());
		moveCards.parameters.put(EventType.Parameter.OBJECT, topFour);
		moveCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(moveCards);
	}
}
