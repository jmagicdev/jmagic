package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bitter Revelation")
@Types({Type.SORCERY})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class BitterRevelation extends Card
{
	public BitterRevelation(GameState state)
	{
		super(state);

		// Look at the top four cards of your library.
		SetGenerator topFour = TopCards.instance(4, LibraryOf.instance(You.instance()));
		EventFactory look = look(You.instance(), topFour, "Look at the top four cards of your library.");
		this.addEffect(look);
		SetGenerator them = EffectResult.instance(look);

		// Put two of them
		EventFactory choose = playerChoose(You.instance(), 2, them, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_INTO_HAND, "Put two of them");
		SetGenerator twoOfThem = EffectResult.instance(choose);

		// into your hand
		this.addEffect(putIntoHand(twoOfThem, You.instance(), "into your hand"));

		// and the rest into your graveyard.
		SetGenerator theRest = RelativeComplement.instance(them, twoOfThem);
		this.addEffect(putIntoGraveyard(theRest, "and the rest into your graveyard."));

		// You lose 2 life.
		this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
	}
}
