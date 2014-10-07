package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Interpret the Signs")
@Types({Type.SORCERY})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class InterprettheSigns extends Card
{
	public InterprettheSigns(GameState state)
	{
		super(state);

		// Scry 3,
		this.addEffect(scry(3, "Scry 3,"));

		// then reveal the top card of your library.
		SetGenerator top = TopCards.instance(1, LibraryOf.instance(You.instance()));
		this.addEffect(reveal(top, "then reveal the top card of your library."));

		// Draw cards equal to that card's converted mana cost.
		SetGenerator N = ConvertedManaCostOf.instance(top);
		this.addEffect(drawCards(You.instance(), N, "Draw cards equal to that card's converted mana cost."));
	}
}
