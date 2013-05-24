package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Jace, Architect of Thought")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.JACE})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class JaceArchitectofThought extends Card
{
	public static final PlayerInterface.ChooseReason CHOOSE_PILE_REASON = new PlayerInterface.ChooseReason("JaceArchitectofThought", "Put a pile into your hand.", true);

	public static final class JaceArchitectofThoughtAbility0 extends LoyaltyAbility
	{
		public static final class DelayedEnd extends UntilNextTurn.EventAndBeginTurnTracker
		{
			public DelayedEnd()
			{
				super(EventType.CREATE_DELAYED_TRIGGER);
			}
		}

		public JaceArchitectofThoughtAbility0(GameState state)
		{
			super(state, +1, "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");

			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, opponentsCreatures);

			EventFactory destroy = ptChangeUntilEndOfTurn(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT), -1, -0, "It gets -1/-0 until end if turn.");
			SetGenerator expires = Not.instance(Intersect.instance(delayedTriggerContext(This.instance()), UntilNextTurn.instance(DelayedEnd.class)));

			EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");
			trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			trigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));
			trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(destroy));
			trigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));
			this.addEffect(trigger);

			state.ensureTracker(new DelayedEnd());
		}
	}

	public static final class JaceArchitectofThoughtAbility1 extends LoyaltyAbility
	{
		public JaceArchitectofThoughtAbility1(GameState state)
		{
			super(state, -2, "Reveal the top three cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other on the bottom of your library in any order.");

			// Reveal the top three cards of your library.
			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator topThree = TopCards.instance(3, yourLibrary);
			this.addEffect(reveal(topThree, "Reveal the top three cards of your library."));

			// An opponent separates those cards into two piles.
			EventFactory chooseOpponent = playerChoose(You.instance(), 1, OpponentsOf.instance(You.instance()), PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.AN_OPPONENT, "");
			this.addEffect(chooseOpponent);

			SetGenerator anOpponent = EffectResult.instance(chooseOpponent);
			EventFactory separate = separateIntoPiles(anOpponent, topThree, 2, "An opponent separates those cards into two piles.");
			this.addEffect(separate);

			// Put one pile into your hand and the other into your graveyard.
			SetGenerator piles = EffectResult.instance(separate);
			EventFactory choosePile = playerChoose(You.instance(), 1, piles, PlayerInterface.ChoiceType.PILE, CHOOSE_PILE_REASON, "");
			this.addEffect(choosePile);

			SetGenerator onePile = EffectResult.instance(choosePile);
			SetGenerator theOtherPile = RelativeComplement.instance(piles, onePile);
			EventFactory toHand = putIntoHand(ExplodeCollections.instance(onePile), You.instance(), "Put one pile into your hand");
			EventFactory toLibrary = putOnBottomOfLibrary(ExplodeCollections.instance(theOtherPile), "and the other on the bottom of your library in any order.");
			this.addEffect(simultaneous(toHand, toLibrary));
		}
	}

	public static final class JaceArchitectofThoughtAbility2 extends LoyaltyAbility
	{
		public JaceArchitectofThoughtAbility2(GameState state)
		{
			super(state, -8, "For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library. You may cast those cards without paying their mana costs.");

			DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search that player's library for a nonland card and exile it, then that player shuffles his or her library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.TARGET, eachPlayer);
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(RelativeComplement.instance(InZone.instance(LibraryOf.instance(eachPlayer)), HasType.instance(Type.LAND))));

			EventFactory forEach = new EventFactory(FOR_EACH_PLAYER, "For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library.");
			forEach.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			forEach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(search));
			this.addEffect(forEach);

			EventFactory cast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast those cards without paying their mana costs.");
			cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cast.parameters.put(EventType.Parameter.OBJECT, ForEachResult.instance(forEach, Players.instance()));
			this.addEffect(cast);
		}
	}

	public JaceArchitectofThought(GameState state)
	{
		super(state);

		this.setPrintedLoyalty((4));

		// +1: Until your next turn, whenever a creature an opponent controls
		// attacks, it gets -1/-0 until end of turn.
		this.addAbility(new JaceArchitectofThoughtAbility0(state));

		// -2: Reveal the top three cards of your library. An opponent separates
		// those cards into two piles. Put one pile into your hand and the other
		// on the bottom of your library in any order.
		this.addAbility(new JaceArchitectofThoughtAbility1(state));

		// -8: For each player, search that player's library for a nonland card
		// and exile it, then that player shuffles his or her library. You may
		// cast those cards without paying their mana costs.
		this.addAbility(new JaceArchitectofThoughtAbility2(state));
	}
}
