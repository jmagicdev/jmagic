package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unexpected Results")
@Types({Type.SORCERY})
@ManaCost("2GU")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class UnexpectedResults extends Card
{
	public UnexpectedResults(GameState state)
	{
		super(state);

		// Shuffle your library, then reveal the top card. If it's a nonland
		// card, you may cast it without paying its mana cost. If it's a land
		// card, you may put it onto the battlefield and return Unexpected
		// Results to its owner's hand.
		this.addEffect(shuffleYourLibrary("Shuffle your library,"));

		EventFactory reveal = reveal(TopCards.instance(1, LibraryOf.instance(You.instance())), "then reveal the top card.");
		this.addEffect(reveal);

		SetGenerator revealedCard = EffectResult.instance(reveal);
		SetGenerator isNotLand = Not.instance(Intersect.instance(TypesOf.instance(revealedCard), Identity.instance(Type.LAND)));

		EventFactory cast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast it without paying its mana cost");
		cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
		cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
		cast.parameters.put(EventType.Parameter.OBJECT, revealedCard);

		EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put it onto the battlefield");
		putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, revealedCard);

		EventFactory returnToHand = bounce(This.instance(), "Return Unexpected Results to its owner's hand");

		EventFactory putOntoBattlefieldAndReturn = simultaneous("Put it onto the battlefield and return Unexpected Results to its owner's hand", putOntoBattlefield, returnToHand);

		this.addEffect(ifThenElse(isNotLand, cast, youMay(putOntoBattlefieldAndReturn), "If it's a nonland card, you may cast it without paying its mana cost. If it's a land card, you may put it onto the battlefield and return Unexpected Results to its owner's hand."));
	}
}
