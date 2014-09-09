package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jump")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Jump extends Card
{
	public Jump(GameState state)
	{
		super(state);

		// Target creature
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// gains flying until end of turn.
		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
	}
}
