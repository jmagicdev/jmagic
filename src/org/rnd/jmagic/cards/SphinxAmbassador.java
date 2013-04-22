package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx Ambassador")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class SphinxAmbassador extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SphinxAmbassador", "Name a card.", true);

	public static final class WtfIsThisDoingInACoreSet extends EventTriggeredAbility
	{
		public WtfIsThisDoingInACoreSet(GameState state)
		{
			// Whenever Sphinx Ambassador deals combat damage to a player,
			super(state, "Whenever Sphinx Ambassador deals combat damage to a player, search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles his or her library.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator tookDamage = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.TARGET, tookDamage);
			parameters.put(EventType.Parameter.CHOICE, CardNames.instance());
			this.addEffect(new EventFactory(SPHINX_AMBASSADOR_EVENT, parameters, "Search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles his or her library."));
		}
	}

	/**
	 * @eparam CAUSE: Sphinx Ambassador's trigger
	 * @eparam PLAYER: Controller of CAUSE
	 * @eparam TARGET: Player who was dealt damage to cause CAUSE to trigger
	 * @eparam CHOICE: The set of card names to choose from
	 */
	public static final EventType SPHINX_AMBASSADOR_EVENT = new EventType("SPHINX_AMBASSADOR_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Player target = parameters.get(Parameter.TARGET).getOne(Player.class);
			java.util.Set<String> names = parameters.get(Parameter.CHOICE).getAll(String.class);

			// search that player's library for a card,
			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, cause);
			searchParameters.put(Parameter.PLAYER, new Set(you));
			searchParameters.put(Parameter.NUMBER, ONE);
			searchParameters.put(Parameter.CARD, new Set(target.getLibrary(game.actualState)));
			Event search = createEvent(game, "Search that player's library for a card.", SEARCH, searchParameters);
			search.perform(event, true);

			// then that player names a card.
			target = target.getActual();
			java.util.List<String> choice = target.choose(1, names, PlayerInterface.ChoiceType.STRING, REASON);
			String namedCard = choice.get(0);

			// If you searched for a creature card that isn't the named card,
			Set searchResult = search.getResult();
			GameObject searchedFor = searchResult.getOne(GameObject.class);
			if(searchedFor.getTypes().contains(Type.CREATURE) && !searchedFor.getName().equals(namedCard))
			{
				// you may put it onto the battlefield under your control.
				EventType.ParameterMap ontoFieldParameters = new EventType.ParameterMap();
				ontoFieldParameters.put(Parameter.CAUSE, Identity.instance(cause));
				ontoFieldParameters.put(Parameter.CONTROLLER, Identity.instance(you));
				ontoFieldParameters.put(Parameter.OBJECT, Identity.instance(searchedFor));

				java.util.Map<Parameter, Set> mayParameters = new java.util.HashMap<Parameter, Set>();
				mayParameters.put(Parameter.CAUSE, cause);
				mayParameters.put(Parameter.PLAYER, new Set(you));
				mayParameters.put(Parameter.EVENT, new Set(new EventFactory(PUT_ONTO_BATTLEFIELD, ontoFieldParameters, "Put it onto the battlefield under your control.")));
				createEvent(game, "You may put it onto the battlefield under your control.", PLAYER_MAY, mayParameters).perform(event, true);
			}

			// Then that player shuffles his or her library.
			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, cause);
			shuffleParameters.put(Parameter.PLAYER, new Set(target));
			createEvent(game, "That player shuffles his or her library.", SHUFFLE_LIBRARY, shuffleParameters).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public SphinxAmbassador(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new WtfIsThisDoingInACoreSet(state));
	}
}
