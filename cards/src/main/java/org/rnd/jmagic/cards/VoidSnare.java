package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Void Snare")
@Types({Type.SORCERY})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VoidSnare extends Card
{
	public VoidSnare(GameState state)
	{
		super(state);

		// Return target nonland permanent to its owner's hand.
		SetGenerator nonland = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator target = targetedBy(this.addTarget(nonland, "target nonland permanent"));
		this.addEffect(bounce(target, "Return target nonland permanent to its owner's hand."));
	}
}
