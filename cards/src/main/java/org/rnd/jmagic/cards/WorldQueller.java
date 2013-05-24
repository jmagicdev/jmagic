package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("World Queller")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class WorldQueller extends Card
{
	/**
	 * @eparam CAUSE: queller's trigger
	 * @eparam PLAYER: controller of CAUSE
	 */
	public static final EventType WORLD_QUELLER_EVENT = new EventType("WORLD_QUELLER_EVENT")
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
			java.util.List<Type> chosenType = you.sanitizeAndChoose(game.actualState, 1, java.util.EnumSet.allOf(Type.class), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_CARD_TYPE);

			Set cause = parameters.get(Parameter.CAUSE);
			java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
			sacParameters.put(Parameter.CAUSE, cause);
			sacParameters.put(Parameter.NUMBER, new Set(1));
			sacParameters.put(Parameter.CHOICE, HasType.get(game.actualState, chosenType));
			sacParameters.put(Parameter.PLAYER, new Set(game.actualState.players));
			Event sacrifice = createEvent(game, "Each player sacrifices a permanent of the chosen type", EventType.SACRIFICE_CHOICE, sacParameters);
			sacrifice.perform(event, true);

			return true;
		}
	};

	public static final class Quell extends EventTriggeredAbility
	{
		public Quell(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may choose a card type. If you do, each player sacrifices a permanent of that type.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory quell = new EventFactory(WORLD_QUELLER_EVENT, "Choose a card type. If you do, each player sacrifices a permanent of that type.");
			quell.parameters.put(EventType.Parameter.CAUSE, This.instance());
			quell.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(youMay(quell, "You may choose a card type. If you do, each player sacrifices a permanent of that type."));
		}
	}

	public WorldQueller(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, you may choose a card type. If you
		// do, each player sacrifices a permanent of that type.
		this.addAbility(new Quell(state));
	}
}
