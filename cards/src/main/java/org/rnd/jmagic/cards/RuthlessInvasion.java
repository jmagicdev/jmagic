package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ruthless Invasion")
@Types({Type.SORCERY})
@ManaCost("3(R/P)")
@ColorIdentity({Color.RED})
public final class RuthlessInvasion extends Card
{
	public RuthlessInvasion(GameState state)
	{
		super(state);

		// Nonartifact creatures can't block this turn.
		this.addEffect(cantBlockThisTurn(RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT)), "Nonartifact creatures can't block this turn."));
	}
}
