package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Last Breath")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class LastBreath extends Card
{
	public LastBreath(GameState state)
	{
		super(state);

		// Exile target creature with power 2 or less.
		SetGenerator legal = HasPower.instance(Between.instance(null, 2));
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature with power 2 or less."));
		this.addEffect(exile(target, "Exile target creature with power 2 or less."));

		// Its controller gains 4 life.
		this.addEffect(gainLife(ControllerOf.instance(target), 4, "Its controller gains 4 life."));
	}
}
