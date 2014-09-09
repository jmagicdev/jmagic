package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Echoing Truth")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class EchoingTruth extends Card
{
	public EchoingTruth(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "target nonland permanent"));

		this.addEffect(bounce(Union.instance(target, Intersect.instance(Permanents.instance(), HasName.instance(NameOf.instance(target)))), "Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands."));
	}
}
