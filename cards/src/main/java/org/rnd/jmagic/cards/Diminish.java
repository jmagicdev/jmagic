package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Diminish")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Diminish extends Card
{
	public Diminish(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(createFloatingEffect("Target creature has base power and toughness 1/1 until end of turn.", setPowerAndToughness(target, 1, 1)));
	}
}
