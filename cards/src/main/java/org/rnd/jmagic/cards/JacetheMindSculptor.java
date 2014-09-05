package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jace, the Mind Sculptor")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.JACE})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class JacetheMindSculptor extends Card
{
	public static final class LibraryManipulation extends LoyaltyAbility
	{
		public LibraryManipulation(GameState state)
		{
			super(state, +2, "Look at the top card of target player's library. You may put that card on the bottom of that player's library.");

			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator thatPlayersLibrary = LibraryOf.instance(targetedBy(target));
			SetGenerator topCard = TopCards.instance(1, thatPlayersLibrary);

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top card of target player's library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topCard);
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(look);

			EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put that card on the bottom of that player's library");
			putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBottom.parameters.put(EventType.Parameter.OBJECT, topCard);
			putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			this.addEffect(youMay(putOnBottom, "You may put that card on the bottom of that player's library."));
		}
	}

	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("JacetheMindSculptor", "Put two cards on top of your library.", false);

	public static final class Brainstorm extends LoyaltyAbility
	{
		public Brainstorm(GameState state)
		{
			super(state, 0, "Draw three cards, then put two cards from your hand on top of your library in any order.");

			this.addEffect(drawCards(You.instance(), 3, "Draw three cards,"));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			parameters.put(EventType.Parameter.REASON, Identity.instance(REASON));
			this.addEffect(new EventFactory(PUT_INTO_LIBRARY_FROM_HAND_CHOICE, parameters, "then put two cards from your hand on top of your library in any order."));
		}
	}

	public static final class Unsummon extends LoyaltyAbility
	{
		public Unsummon(GameState state)
		{
			super(state, -1, "Return target creature to its owner's hand.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));
		}
	}

	public static final class ProbablyWin extends LoyaltyAbility
	{
		public ProbablyWin(GameState state)
		{
			super(state, -12, "Exile all cards from target player's library, then that player shuffles his or her hand into his or her library.");

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(exile(InZone.instance(LibraryOf.instance(targetedBy(target))), "Exile all cards from target player's library,"));

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "then that player shuffles his or her hand into his or her library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(HandOf.instance(targetedBy(target))), targetedBy(target)));
			this.addEffect(shuffle);
		}
	}

	public JacetheMindSculptor(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +2: Look at the top card of target player's library. You may put that
		// card on the bottom of that player's library.
		this.addAbility(new LibraryManipulation(state));

		// 0: Draw three cards, then put two cards from your hand on top of your
		// library in any order.
		this.addAbility(new Brainstorm(state));

		// -1: Return target creature to its owner's hand.
		this.addAbility(new Unsummon(state));

		// -12: Exile all cards from target player's library, then that player
		// shuffles his or her hand into his or her library.
		this.addAbility(new ProbablyWin(state));
	}
}
