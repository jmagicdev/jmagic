package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("In Garruk's Wake")
@Types({Type.SORCERY})
@ManaCost("7BB")
@ColorIdentity({Color.BLACK})
public final class InGarruksWake extends Card
{
	public InGarruksWake(GameState state)
	{
		super(state);

		// Destroy all creatures you don't control and all planeswalkers you
		// don't control.
		SetGenerator stuff = Intersect.instance(Permanents.instance(), HasType.instance(Type.CREATURE, Type.PLANESWALKER));
		SetGenerator notYours = RelativeComplement.instance(stuff, ControlledBy.instance(You.instance()));
		this.addEffect(destroy(notYours, "Destroy all creatures you don't control and all planeswalkers you don't control."));
	}
}
