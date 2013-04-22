package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("J\u00F6tun Grunt")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.GIANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class JotunGrunt extends Card
{
	public static final class JotunGruntAbility0 extends org.rnd.jmagic.abilities.keywords.CumulativeUpkeep
	{
		public static final PlayerInterface.ChooseReason CHOOSE_GRAVEYARD = new PlayerInterface.ChooseReason("J\u00F6tun Grunt", "Choose a player with a graveyard with at least two cards", true);

		public static final PlayerInterface.ChooseReason CHOOSE_CARDS = new PlayerInterface.ChooseReason("J\u00F6tun Grunt", "Choose two cards to put on the bottom of their owner's library", true);

		public JotunGruntAbility0(GameState state)
		{
			super(state, "Put two cards from a single graveyard on the bottom of their owner's library.");
		}

		@Override
		protected EventFactory getFactory(SetGenerator thisAbility)
		{
			DynamicEvaluation dynamic = DynamicEvaluation.instance();
			SetGenerator cardsInGraveyard = Count.instance(InZone.instance(GraveyardOf.instance(dynamic)));
			SetGenerator hasTwoOrMore = Intersect.instance(cardsInGraveyard, Between.instance(2, null));
			SetGenerator ifThen = IfThenElse.instance(hasTwoOrMore, dynamic, Empty.instance());
			SetGenerator legalPlayers = ForEach.instance(Players.instance(), ifThen, dynamic);

			EventFactory chooseGraveyard = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a player with a graveyard with at least two cards");
			chooseGraveyard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			chooseGraveyard.parameters.put(EventType.Parameter.CHOICE, legalPlayers);
			chooseGraveyard.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.PLAYER, CHOOSE_GRAVEYARD));

			SetGenerator chosenPlayer = effectResultFrom(chooseGraveyard, thisAbility);

			EventFactory putTwoCards = new EventFactory(EventType.MOVE_CHOICE, "then put two cards from that graveyard on the bottom of their owner's library");
			putTwoCards.parameters.put(EventType.Parameter.CAUSE, thisAbility);
			putTwoCards.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			putTwoCards.parameters.put(EventType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(chosenPlayer)));
			putTwoCards.parameters.put(EventType.Parameter.CHOICE, Identity.instance(CHOOSE_CARDS));
			putTwoCards.parameters.put(EventType.Parameter.TO, LibraryOf.instance(chosenPlayer));
			putTwoCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
			putTwoCards.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));

			return sequence(chooseGraveyard, putTwoCards);
		}
	}

	public JotunGrunt(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Cumulative upkeep\u2014Put two cards from a single graveyard on the
		// bottom of their owner's library. (At the beginning of your upkeep,
		// put an age counter on this permanent, then sacrifice it unless you
		// pay its upkeep cost for each age counter on it.)
		this.addAbility(new JotunGruntAbility0(state));
	}
}
