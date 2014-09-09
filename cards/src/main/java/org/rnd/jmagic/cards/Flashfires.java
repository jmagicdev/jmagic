package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flashfires")
@Types({Type.SORCERY})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class Flashfires extends Card
{
	public Flashfires(GameState state)
	{
		super(state);

		// Destroy all Plains.
		this.addEffect(destroy(Intersect.instance(LandPermanents.instance(), HasSubType.instance(SubType.PLAINS)), "Destroy all Plains."));
	}
}
