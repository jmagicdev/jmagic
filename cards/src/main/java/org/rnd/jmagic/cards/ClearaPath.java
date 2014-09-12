package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Clear a Path")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class ClearaPath extends Card
{
	public ClearaPath(GameState state)
	{
		super(state);

		// Destroy target creature with defender.
		SetGenerator creatureWithDefender = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class));
		SetGenerator target = targetedBy(this.addTarget(creatureWithDefender, "target creature with defender"));
		this.addEffect(destroy(target, "Destroy target creature with defender."));
	}
}
