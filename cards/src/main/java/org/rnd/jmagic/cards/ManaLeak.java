package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Leak")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ManaLeak extends Card
{
	public ManaLeak(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(3)", target));
	}
}
