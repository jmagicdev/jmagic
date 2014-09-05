package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Conundrum Sphinx")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ConundrumSphinx extends Card
{
	public static final class ConundrumSphinxAbility1 extends EventTriggeredAbility
	{
		public ConundrumSphinxAbility1(GameState state)
		{
			super(state, "Whenever Conundrum Sphinx attacks, each player names a card. Then each player reveals the top card of his or her library. If the card a player revealed is the card he or she named, that player puts it into his or her hand. If it's not, that player puts it on the bottom of his or her library.");
			this.addPattern(whenThisAttacks());

			DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

			EventFactory someoneNamesACard = new EventFactory(EventType.PLAYER_CHOOSE, "Name a card.");
			someoneNamesACard.parameters.put(EventType.Parameter.CHOICE, CardNames.instance());
			someoneNamesACard.parameters.put(EventType.Parameter.PLAYER, eachPlayer);
			someoneNamesACard.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_CARD));

			EventFactory eachPlayerNamesACard = new EventFactory(FOR_EACH_PLAYER, "Each player names a card.");
			eachPlayerNamesACard.parameters.put(EventType.Parameter.EFFECT, Identity.instance(someoneNamesACard));
			eachPlayerNamesACard.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			this.addEffect(eachPlayerNamesACard);

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Then each player reveals the top card of his or her library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(Players.instance())));
			this.addEffect(reveal);

			SetGenerator library = LibraryOf.instance(eachPlayer);
			SetGenerator revealedCard = TopCards.instance(1, library);
			SetGenerator revealedCardName = NameOf.instance(revealedCard);
			SetGenerator namedCard = ForEachResult.instance(eachPlayerNamesACard, eachPlayer);

			EventFactory putIntoHand = new EventFactory(EventType.MOVE_OBJECTS, "That player puts the revealed card into his or her hand");
			putIntoHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putIntoHand.parameters.put(EventType.Parameter.TO, HandOf.instance(eachPlayer));
			putIntoHand.parameters.put(EventType.Parameter.OBJECT, revealedCard);

			EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "That player puts the revealed card on the bottom of his or her library");
			putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBottom.parameters.put(EventType.Parameter.OBJECT, revealedCard);
			putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));

			EventFactory ifThenElse = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If the card a player revealed is the card he or she named, that player puts it into his or her hand; if it's not, that player puts it on the bottom of his or her library");
			ifThenElse.parameters.put(EventType.Parameter.IF, Intersect.instance(revealedCardName, namedCard));
			ifThenElse.parameters.put(EventType.Parameter.THEN, Identity.instance(putIntoHand));
			ifThenElse.parameters.put(EventType.Parameter.ELSE, Identity.instance(putOnBottom));

			EventFactory eachPlayerMovesCards = new EventFactory(FOR_EACH_PLAYER, "If the card a player revealed is the card he or she named, that player puts it into his or her hand. If it's not, that player puts it on the bottom of his or her library.");
			eachPlayerMovesCards.parameters.put(EventType.Parameter.EFFECT, Identity.instance(ifThenElse));
			eachPlayerMovesCards.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			this.addEffect(eachPlayerMovesCards);
		}
	}

	public ConundrumSphinx(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Conundrum Sphinx attacks, each player names a card. Then
		// each player reveals the top card of his or her library. If the card a
		// player revealed is the card he or she named, that player puts it into
		// his or her hand. If it's not, that player puts it on the bottom of
		// his or her library.
		this.addAbility(new ConundrumSphinxAbility1(state));
	}
}
