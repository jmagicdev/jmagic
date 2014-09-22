package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sovereigns of Lost Alara")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class SovereignsofLostAlara extends Card
{
	/**
	 * @eparam CAUSE: sovereigns' ability
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam ATTACKER: creature that attacked to trigger CAUSE
	 * @eparam CARD: all the cards in PLAYER's library
	 */
	public static final EventType SOVEREIGNS_OF_LOST_ALARA_EVENT = new EventType("SOVEREIGNS_OF_LOST_ALARA_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			// Search your library for an Aura card that could enchant that
			// creature,
			GameObject thatCreature = parameters.get(Parameter.ATTACKER).getOne(GameObject.class);
			Set cardsInLibrary = parameters.get(Parameter.CARD);
			Set choices = new Set();
			for(GameObject o: cardsInLibrary.getAll(GameObject.class))
				if(o.getSubTypes().contains(SubType.AURA))
				{
					java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
					attachParameters.put(EventType.Parameter.OBJECT, new Set(o));
					attachParameters.put(EventType.Parameter.TARGET, new Set(thatCreature));
					if(ATTACH.attempt(game, event, attachParameters))
						choices.add(o);
				}

			Set cause = parameters.get(Parameter.CAUSE);
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(EventType.Parameter.CAUSE, cause);
			searchParameters.put(EventType.Parameter.PLAYER, you);
			searchParameters.put(EventType.Parameter.NUMBER, ONE);
			searchParameters.put(EventType.Parameter.CARD, cardsInLibrary);
			searchParameters.put(EventType.Parameter.TYPE, new Set(Identity.fromCollection(choices)));
			Event search = createEvent(game, "Search your library for an Aura card that could enchant that creature", SEARCH, searchParameters);
			search.perform(event, true);

			// put it onto the battlefield attached to that creature,
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(EventType.Parameter.CAUSE, cause);
			moveParameters.put(EventType.Parameter.CONTROLLER, you);
			moveParameters.put(EventType.Parameter.OBJECT, search.getResult());
			moveParameters.put(EventType.Parameter.TARGET, new Set(thatCreature));
			Event move = createEvent(game, "Put it onto the battlefield attached to that creature", PUT_ONTO_BATTLEFIELD_ATTACHED_TO, moveParameters);
			move.perform(event, true);

			// then shuffle your library.
			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(EventType.Parameter.CAUSE, cause);
			shuffleParameters.put(EventType.Parameter.PLAYER, you);
			Event shuffle = createEvent(game, "Shuffle your library", SHUFFLE_LIBRARY, shuffleParameters);
			shuffle.perform(event, true);

			return true;
		}
	};

	public static final class GetEldraziConscription extends org.rnd.jmagic.abilityTemplates.ExaltedBase
	{
		public GetEldraziConscription(GameState state)
		{
			super(state, "Whenever a creature you control attacks alone, you may search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library.");

			EventFactory effect = new EventFactory(SOVEREIGNS_OF_LOST_ALARA_EVENT, "Search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.ATTACKER, EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT));
			effect.parameters.put(EventType.Parameter.CARD, InZone.instance(LibraryOf.instance(You.instance())));
			this.addEffect(youMay(effect, "You may search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library."));
		}
	}

	public SovereignsofLostAlara(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Exalted
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// Whenever a creature you control attacks alone, you may search your
		// library for an Aura card that could enchant that creature, put it
		// onto the battlefield attached to that creature, then shuffle your
		// library.
		this.addAbility(new GetEldraziConscription(state));
	}
}
