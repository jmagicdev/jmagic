package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("End Hostilities")
@Types({Type.SORCERY})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class EndHostilities extends Card
{
	public EndHostilities(GameState state)
	{
		super(state);

		// Destroy all creatures and all permanents attached to creatures.
		SetGenerator stuff = Intersect.instance(CreaturePermanents.instance(), AttachedTo.instance(CreaturePermanents.instance()));
		this.addEffect(destroy(stuff, "Destroy all creatures and all permanents attached to creatures."));
	}
}
