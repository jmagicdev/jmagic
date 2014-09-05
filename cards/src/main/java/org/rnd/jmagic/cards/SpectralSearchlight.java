package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spectral Searchlight")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SpectralSearchlight extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SpectralSearchlight", "Choose a player.", true);

	public static final class SearchlightMana extends ActivatedAbility
	{
		/**
		 * @eparam SOURCE: the source of the mana
		 * @eparam PLAYER: the player choosing
		 * @eparam CHOICE: the players to choose from
		 * @eparam MANA: the mana to add to the chosen players pool
		 * @eparam RESULT: the result of ADD_MANA
		 */
		public static EventType SPECTRAL_SEARCHLIGHT_EVENT = new EventType("SPECTRAL_SEARCHLIGHT_EVENT")
		{
			@Override
			public boolean addsMana()
			{
				return true;
			}

			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
				java.util.Set<Player> choices = parameters.get(Parameter.CHOICE).getAll(Player.class);
				java.util.Collection<Player> choice = you.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.PLAYER, REASON);

				java.util.Map<EventType.Parameter, Set> addManaParameters = new java.util.HashMap<EventType.Parameter, Set>();
				addManaParameters.put(EventType.Parameter.SOURCE, parameters.get(EventType.Parameter.SOURCE));
				addManaParameters.put(EventType.Parameter.PLAYER, Set.fromCollection(choice));
				addManaParameters.put(EventType.Parameter.MANA, parameters.get(EventType.Parameter.MANA));
				Event addMana = createEvent(game, "That player adds one mana of any color he or she chooses to his or her mana pool.", EventType.ADD_MANA, addManaParameters);
				boolean ret = addMana.perform(event, false);

				event.setResult(addMana.getResult());
				return ret;
			}
		};

		public SearchlightMana(GameState state)
		{
			super(state, "(T): Choose a player. That player adds one mana of any color he or she chooses to his or her mana pool.");
			this.costsTap = true;

			EventType.ParameterMap addManaParameters = new EventType.ParameterMap();
			addManaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addManaParameters.put(EventType.Parameter.PLAYER, You.instance());
			addManaParameters.put(EventType.Parameter.CHOICE, Players.instance());
			addManaParameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			this.addEffect(new EventFactory(SPECTRAL_SEARCHLIGHT_EVENT, addManaParameters, "Choose a player. That player adds one mana of any color he or she chooses to his or her mana pool"));
		}
	}

	public SpectralSearchlight(GameState state)
	{
		super(state);

		this.addAbility(new SearchlightMana(state));
	}
}
