package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mass Calcify")
@Types({Type.SORCERY})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class MassCalcify extends Card
{
	public MassCalcify(GameState state)
	{
		super(state);

		// Destroy all nonwhite creatures.
		SetGenerator racist = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
		this.addEffect(destroy(racist, "Destroy all nonwhite creatures."));
	}
}
