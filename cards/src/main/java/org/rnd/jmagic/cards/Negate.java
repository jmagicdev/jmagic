package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Negate")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Negate extends Card
{
	public Negate(GameState state)
	{
		super(state);

		Target target = this.addTarget(RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)), "target noncreature spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target noncreature spell."));
	}
}
