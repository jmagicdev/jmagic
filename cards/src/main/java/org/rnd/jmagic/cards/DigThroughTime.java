package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dig Through Time")
@Types({Type.INSTANT})
@ManaCost("6UU")
@ColorIdentity({Color.BLUE})
public final class DigThroughTime extends Card
{
	public DigThroughTime(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Look at the top seven cards of your library.
		SetGenerator top = TopCards.instance(7, LibraryOf.instance(You.instance()));
		EventFactory look = look(You.instance(), top, "Look at the top seven cards of your library.");
		this.addEffect(look);
		SetGenerator lookedAt = EffectResult.instance(look);

		// Put two of them
		EventFactory choose = playerChoose(You.instance(), 2, lookedAt, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_INTO_HAND, "Put two of them");
		this.addEffect(choose);
		SetGenerator twoOfThem = EffectResult.instance(choose);

		// into your hand
		this.addEffect(putIntoHand(twoOfThem, You.instance(), "into your hand"));

		// and the rest on the bottom of your library in any order.
		this.addEffect(putOnBottomOfLibrary(lookedAt, "and the rest on the bottom of your library in any order."));
	}
}
