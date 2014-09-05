package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Surreal Memoir")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SurrealMemoir extends Card
{
	public static final EventType SURREAL_MEMOIR_EFFECT = new EventType("SURREAL_MEMOIR_EFFECT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone graveyard = you.getGraveyard(game.actualState);

			java.util.List<GameObject> instants = new java.util.LinkedList<GameObject>();
			for(GameObject card: graveyard)
				if(card.getTypes().contains(org.rnd.jmagic.engine.Type.INSTANT))
					instants.add(card);
			if(!instants.isEmpty())
			{
				java.util.Collections.shuffle(instants);

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, new Set(event.getSource()));
				moveParameters.put(Parameter.TO, new Set(you.getHand(game.actualState)));
				moveParameters.put(Parameter.OBJECT, new Set(instants.iterator().next()));
				createEvent(game, "Return an instant card at random from your graveyard to your hand.", EventType.MOVE_OBJECTS, moveParameters).perform(event, true);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public SurrealMemoir(GameState state)
	{
		super(state);

		// Return an instant card at random from your graveyard to your hand.
		EventFactory effect = new EventFactory(SURREAL_MEMOIR_EFFECT, "Return an instant card at random from your graveyard to your hand.");
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(effect);

		// Rebound
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
