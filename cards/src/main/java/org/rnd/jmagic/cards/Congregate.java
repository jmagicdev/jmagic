package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Congregate")
@Types({Type.INSTANT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class Congregate extends Card
{
	public Congregate(GameState state)
	{
		super(state);

		// Target player gains 2 life for each creature on the battlefield.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator twiceCreatures = Multiply.instance(numberGenerator(2), Count.instance(CreaturePermanents.instance()));
		this.addEffect(gainLife(target, twiceCreatures, "Target player gains 2 life for each creature on the battlefield."));
	}
}
