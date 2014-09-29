package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Taigam's Scheming")
@Types({Type.SORCERY})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TaigamsScheming extends Card
{
	public TaigamsScheming(GameState state)
	{
		super(state);

		// Look at the top five cards of your library.

		// Put any number of them into your graveyard and the rest back on top
		// of your library in any order.

		SetGenerator top = TopCards.instance(5, LibraryOf.instance(You.instance()));
		EventFactory look = look(You.instance(), top, "Look at the top five cards of your library.");
		this.addEffect(look);
		SetGenerator them = EffectResult.instance(look);

		EventFactory choose = playerChoose(You.instance(), Between.instance(0, null), them, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.DISCARD, "Put any number of them");
		this.addEffect(choose);
		SetGenerator chosen = EffectResult.instance(choose);

		this.addEffect(putIntoGraveyard(chosen, "into your graveyard"));
		this.addEffect(putOnTopOfLibrary(them, "and the rest back on top of your library in any order."));
	}
}
