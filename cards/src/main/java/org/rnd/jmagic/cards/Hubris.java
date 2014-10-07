package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hubris")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Hubris extends Card
{
	public Hubris(GameState state)
	{
		super(state);

		// Return target creature and all Auras attached to it to their owners'
		// hands.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator attached = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(target));
		this.addEffect(bounce(Union.instance(target, attached), "Return target creature and all Auras attached to it to their owners' hands."));
	}
}
