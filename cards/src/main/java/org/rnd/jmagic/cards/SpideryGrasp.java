package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spidery Grasp")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class SpideryGrasp extends Card
{
	public SpideryGrasp(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Untap target creature.
		this.addEffect(untap(target, "Untap target creature."));

		// It gets +2/+4 and gains reach until end of turn. (It can block
		// creatures with flying.)
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +4, "It gets +2/+4 and gains reach until end of turn.", org.rnd.jmagic.abilities.keywords.Reach.class));
	}
}
