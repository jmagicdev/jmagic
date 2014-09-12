package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Festergloom")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class Festergloom extends Card
{
	public Festergloom(GameState state)
	{
		super(state);

		// Nonblack creatures get -1/-1 until end of turn.
		SetGenerator nonBlackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		this.addEffect(ptChangeUntilEndOfTurn(nonBlackCreatures, -1, -1, "Nonblack creatures get -1/-1 until end of turn."));
	}
}
