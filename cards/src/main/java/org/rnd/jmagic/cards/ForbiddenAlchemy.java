package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Forbidden Alchemy")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ForbiddenAlchemy extends Card
{
	public static final PlayerInterface.ChooseReason CHOOSE_REASON = new PlayerInterface.ChooseReason("Forbidden Alchemy", "Put one of those cards into your hand.", false);

	public ForbiddenAlchemy(GameState state)
	{
		super(state);

		SetGenerator yourLibrary = LibraryOf.instance(You.instance());

		// Look at the top four cards of your library. Put one of them into your
		// hand and the rest into your graveyard.
		EventFactory look = new EventFactory(EventType.LOOK, "Look at the top four cards of your library.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		look.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(4, yourLibrary));
		this.addEffect(look);

		SetGenerator lookedCards = EffectResult.instance(look);
		EventFactory choose = playerChoose(You.instance(), 1, lookedCards, PlayerInterface.ChoiceType.OBJECTS, CHOOSE_REASON, "");
		this.addEffect(choose);

		SetGenerator chosen = EffectResult.instance(choose);
		SetGenerator rest = RelativeComplement.instance(lookedCards, chosen);

		EventFactory hand = putIntoHand(chosen, You.instance(), "Put one of them into your hand,");
		EventFactory graveyard = putIntoGraveyard(rest, "and the rest into your graveyard.");
		this.addEffect(simultaneous(hand, graveyard));

		// Flashback (6)(B) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(6)(B)"));
	}
}
