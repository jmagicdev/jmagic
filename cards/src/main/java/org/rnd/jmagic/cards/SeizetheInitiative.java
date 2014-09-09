package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seize the Initiative")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class SeizetheInitiative extends Card
{
	public SeizetheInitiative(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains first strike until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
