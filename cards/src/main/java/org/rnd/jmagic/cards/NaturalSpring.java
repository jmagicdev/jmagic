package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Natural Spring")
@Types({Type.SORCERY})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class NaturalSpring extends Card
{
	public NaturalSpring(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(gainLife(targetedBy(target), 8, "Target player gains 8 life."));
	}
}
