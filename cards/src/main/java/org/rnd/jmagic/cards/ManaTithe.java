package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Tithe")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class ManaTithe extends Card
{
	public ManaTithe(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (1).
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(1)", target));
	}
}
