package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sanguimancy")
@Types({Type.SORCERY})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class Sanguimancy extends Card
{
	public Sanguimancy(GameState state)
	{
		super(state);

		// You draw X cards and you lose X life, where X is your devotion to
		// black.
		SetGenerator devotion = DevotionTo.instance(Color.BLACK);
		this.addEffect(drawCards(You.instance(), devotion, "You draw X cards"));
		this.addEffect(loseLife(You.instance(), devotion, "and you lose X life, where X is your devotion to black."));
	}
}
