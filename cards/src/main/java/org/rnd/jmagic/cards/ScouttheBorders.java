package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scout the Borders")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ScouttheBorders extends Card
{
	public ScouttheBorders(GameState state)
	{
		super(state);

		// Reveal the top five cards of your library.
		SetGenerator top = TopCards.instance(5, LibraryOf.instance(You.instance()));
		EventFactory reveal = reveal(top, "Reveal the top five cards of your library.");
		this.addEffect(reveal);
		SetGenerator them = EffectResult.instance(reveal);

		// You may put a creature or land card from among them
		SetGenerator chooseable = Intersect.instance(them, HasType.instance(Type.CREATURE, Type.LAND));
		EventFactory fromAmongThem = playerChoose(You.instance(), Between.instance(0, 1), chooseable, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_INTO_HAND, "You may put a creature or land card from among them");
		this.addEffect(fromAmongThem);
		SetGenerator chosen = EffectResult.instance(fromAmongThem);

		// into your hand.
		this.addEffect(putIntoHand(chosen, You.instance(), "into your hand."));

		// Put the rest into your graveyard.
		this.addEffect(putIntoGraveyard(them, "Put the rest into your graveyard."));
	}
}
