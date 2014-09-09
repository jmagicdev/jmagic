package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cruel Edict")
@Types({Type.SORCERY})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class CruelEdict extends Card
{
	public CruelEdict(GameState state)
	{
		super(state);

		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

		SetGenerator targetOpponent = targetedBy(target);
		this.addEffect(sacrifice(targetOpponent, 1, CreaturePermanents.instance(), "Target opponent sacrifices a creature."));
	}
}
