package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Djinn of Wishes")
@Types({Type.CREATURE})
@SubTypes({SubType.DJINN})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DjinnofWishes extends Card
{
	public static final class Wish extends ActivatedAbility
	{
		/**
		 * @eparam CAUSE: the Wish ability
		 * @eparam OBJECT: the card to reveal and potentially play
		 * @eparam PLAYER: the player being given the choice
		 * @eparam RESULT: empty
		 */
		public static final EventType DJINN_OF_WISHES_EVENT = new EventType("DJINN_OF_WISHES_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set cause = parameters.get(EventType.Parameter.CAUSE);
				Set object = parameters.get(EventType.Parameter.OBJECT);
				Set player = parameters.get(EventType.Parameter.PLAYER);

				java.util.Map<EventType.Parameter, Set> revealParameters = new java.util.HashMap<EventType.Parameter, Set>();
				revealParameters.put(EventType.Parameter.CAUSE, cause);
				revealParameters.put(EventType.Parameter.OBJECT, object);
				Event revealEvent = createEvent(game, "Reveal the top card of your library.", EventType.REVEAL, revealParameters);
				boolean ret = revealEvent.perform(event, true);

				EventType.ParameterMap playParameters = new EventType.ParameterMap();
				playParameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
				playParameters.put(EventType.Parameter.PLAYER, Identity.instance(player));
				playParameters.put(EventType.Parameter.OBJECT, Identity.instance(object));
				playParameters.put(EventType.Parameter.ALTERNATE_COST, Empty.instance());
				Set playEvent = new Set(new EventFactory(EventType.PLAY_CARD, playParameters, "Play that card without paying its mana cost."));

				EventType.ParameterMap mayParameters = new EventType.ParameterMap();
				mayParameters.put(EventType.Parameter.PLAYER, Identity.instance(player));
				mayParameters.put(EventType.Parameter.EVENT, Identity.instance(playEvent));
				Set mayEvent = new Set(new EventFactory(EventType.PLAYER_MAY, mayParameters, "You may play that card without paying its mana cost."));

				EventFactory exileFactory = new EventFactory(EventType.MOVE_OBJECTS, "Exile it.");
				exileFactory.parameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
				exileFactory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
				exileFactory.parameters.put(EventType.Parameter.OBJECT, Identity.instance(object));
				Set exileEvent = new Set(exileFactory);

				java.util.Map<EventType.Parameter, Set> ifParameters = new java.util.HashMap<EventType.Parameter, Set>();
				ifParameters.put(EventType.Parameter.IF, mayEvent);
				ifParameters.put(EventType.Parameter.ELSE, exileEvent);
				Event ifEvent = createEvent(game, "You may play that card without paying its mana cost. If you don't, exile it.", EventType.IF_EVENT_THEN_ELSE, ifParameters);
				ret = ifEvent.perform(event, true) && ret;

				event.setResult(Empty.set);

				return ret;
			}
		};

		public Wish(GameState state)
		{
			super(state, "(2)(U)(U), Remove a wish counter from Djinn of Wishes: Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it.");

			this.setManaCost(new ManaPool("2UU"));

			EventType.ParameterMap costParameters = new EventType.ParameterMap();
			costParameters.put(EventType.Parameter.CAUSE, This.instance());
			costParameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.WISH));
			costParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			costParameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addCost(new EventFactory(EventType.REMOVE_COUNTERS, costParameters, "Remove a wish counter from Djinn of Wishes."));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(You.instance())));
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(DJINN_OF_WISHES_EVENT, parameters, "Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it."));
		}
	}

	public DjinnofWishes(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 3, Counter.CounterType.WISH));
		this.addAbility(new Wish(state));
	}
}
