package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Transmute Artifact")
@Types({Type.SORCERY})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TransmuteArtifact extends Card
{
	public TransmuteArtifact(GameState state)
	{
		super(state);

		// Sacrifice an artifact.
		EventFactory sacrifice = sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact");
		SetGenerator sacrificedArtifact = OldObjectOf.instance(EffectResult.instance(sacrifice));

		// If you do, search your library for an artifact card.
		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for an artifact card");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));

		SetGenerator thatCard = EffectResult.instance(search);

		// If that card's converted mana cost is less than or equal to the
		// sacrificed artifact's converted mana cost, put it onto the
		// battlefield.
		EventFactory putOntoField = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put it onto the battlefield");
		putOntoField.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoField.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		putOntoField.parameters.put(EventType.Parameter.OBJECT, thatCard);

		SetGenerator thatCardCMC = ConvertedManaCostOf.instance(thatCard);
		SetGenerator saccedArtifactCMC = ConvertedManaCostOf.instance(sacrificedArtifact);
		SetGenerator difference = Subtract.instance(thatCardCMC, saccedArtifactCMC);

		// If it's greater, you may pay (X), where X is the difference. If you
		// do, put it onto the battlefield.
		EventFactory youMayPayX = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (X), where X is the difference");
		youMayPayX.parameters.put(EventType.Parameter.CAUSE, This.instance());
		youMayPayX.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
		youMayPayX.parameters.put(EventType.Parameter.NUMBER, difference);
		youMayPayX.parameters.put(EventType.Parameter.PLAYER, You.instance());

		// If you don't, put it into its owner's graveyard.
		EventFactory putIntoYard = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Put it into its owner's graveyard");
		putIntoYard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putIntoYard.parameters.put(EventType.Parameter.OBJECT, thatCard);

		EventFactory payOrToss = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (X), where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard.");
		payOrToss.parameters.put(EventType.Parameter.IF, Identity.instance(youMayPayX));
		payOrToss.parameters.put(EventType.Parameter.THEN, Identity.instance(putOntoField));
		payOrToss.parameters.put(EventType.Parameter.ELSE, Identity.instance(putIntoYard));

		SetGenerator lessThanOrEqual = Intersect.instance(thatCardCMC, Between.instance(Empty.instance(), saccedArtifactCMC));
		SetGenerator onePlusSaccedCMC = Sum.instance(Union.instance(numberGenerator(1), saccedArtifactCMC));
		SetGenerator greaterThan = Intersect.instance(thatCardCMC, Between.instance(onePlusSaccedCMC, Empty.instance()));

		EventFactory ifBigger = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's greater, you may pay (X), where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard.");
		ifBigger.parameters.put(EventType.Parameter.IF, greaterThan);
		ifBigger.parameters.put(EventType.Parameter.THEN, Identity.instance(payOrToss));

		EventFactory ifSmallerOrBigger = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay (X), where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard.");
		ifSmallerOrBigger.parameters.put(EventType.Parameter.IF, lessThanOrEqual);
		ifSmallerOrBigger.parameters.put(EventType.Parameter.THEN, Identity.instance(putOntoField));
		ifSmallerOrBigger.parameters.put(EventType.Parameter.ELSE, Identity.instance(ifBigger));

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay (X), where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(sacrifice));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(search, ifSmallerOrBigger)));
		this.addEffect(effect);

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
