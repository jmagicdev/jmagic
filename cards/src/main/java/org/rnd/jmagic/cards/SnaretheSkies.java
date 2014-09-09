package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snare the Skies")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SnaretheSkies extends Card
{
	public SnaretheSkies(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains reach until end of turn. (It can
		// block creatures with flying.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains reach until end of turn.", org.rnd.jmagic.abilities.keywords.Reach.class));
	}
}
