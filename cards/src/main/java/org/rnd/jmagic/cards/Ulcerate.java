package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ulcerate")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Ulcerate extends Card
{
	public Ulcerate(GameState state)
	{
		super(state);

		// Target creature gets -3/-3 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -3, -3, "Target creature gets -3/-3 until end of turn."));

		// You lose 3 life.
		this.addEffect(loseLife(You.instance(), 3, "You lose 3 life."));
	}
}
