package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Read the Bones")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class ReadtheBones extends Card
{
	public ReadtheBones(GameState state)
	{
		super(state);

		// Scry 2,
		this.addEffect(scry(2, "Scry 2,"));

		// then draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "then draw two cards."));

		// You lose 2 life.
		this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
	}
}
