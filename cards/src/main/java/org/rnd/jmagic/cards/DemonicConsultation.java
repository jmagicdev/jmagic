package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonic Consultation")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DemonicConsultation extends Card
{
	public DemonicConsultation(GameState state)
	{
		super(state);

		// Name a card.
		EventFactory nameACard = new EventFactory(EventType.PLAYER_CHOOSE, "Name a card.");
		nameACard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		nameACard.parameters.put(EventType.Parameter.CHOICE, CardNames.instance());
		nameACard.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_CARD));
		this.addEffect(nameACard);

		// Exile the top six cards of your library,
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		this.addEffect(exile(TopCards.instance(6, yourLibrary), "Exile the top six cards of your library,"));

		// then reveal cards from the top of your library until you reveal the
		// named card.
		SetGenerator namedCard = HasName.instance(EffectResult.instance(nameACard));
		SetGenerator untilTheNamedCard = TopMost.instance(yourLibrary, numberGenerator(1), namedCard);
		this.addEffect(reveal(untilTheNamedCard, "then reveal cards from the top of your library until you reveal the named card."));

		// Put that card into your hand
		SetGenerator thatCard = Intersect.instance(namedCard, untilTheNamedCard);
		this.addEffect(putIntoHand(thatCard, You.instance(), "Put that card into your hand"));

		// and exile all other cards revealed this way.
		SetGenerator allOtherCards = RelativeComplement.instance(untilTheNamedCard, namedCard);
		this.addEffect(exile(allOtherCards, "and exile all other cards revealed this way."));
	}
}
