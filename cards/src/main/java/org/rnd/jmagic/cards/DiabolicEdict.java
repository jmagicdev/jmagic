package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Diabolic Edict")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class DiabolicEdict extends Card
{
	public DiabolicEdict(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator targetOpponent = targetedBy(target);
		this.addEffect(sacrifice(targetOpponent, 1, CreaturePermanents.instance(), "Target player sacrifices a creature."));
	}
}
