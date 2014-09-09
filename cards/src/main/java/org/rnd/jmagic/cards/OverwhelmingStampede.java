package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Overwhelming Stampede")
@Types({Type.SORCERY})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class OverwhelmingStampede extends Card
{
	public OverwhelmingStampede(GameState state)
	{
		super(state);

		SetGenerator X = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));

		// Until end of turn, creatures you control gain trample and get +X/+X,
		// where X is the greatest power among creatures you control.
		this.addEffect(createFloatingEffect("Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control.", modifyPowerAndToughness(CREATURES_YOU_CONTROL, X, X), addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Trample.class)));
	}
}
