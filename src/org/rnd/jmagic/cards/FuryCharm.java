package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fury Charm")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FuryCharm extends Card
{
	public FuryCharm(GameState state)
	{
		super(state);

		// Destroy target artifact
		{
			Target target = this.addTarget(1, ArtifactPermanents.instance(), "target artifact");
			this.addEffect(1, destroy(targetedBy(target), "Destroy target artifact"));
		}

		// Target creature gets +1/+1 and gains trample until end of turn
		{
			Target target = this.addTarget(2, CreaturePermanents.instance(), "target creature");
			this.addEffect(2, ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +1, +1, "target creature gets +1/+1 and gains trample until end of turn", org.rnd.jmagic.abilities.keywords.Trample.class));
		}

		// Remove two time counters from target permanent or suspended card.
		{
			Target target = this.addTarget(3, Union.instance(Permanents.instance(), Suspended.instance()), "target permanent or suspended card");
			this.addEffect(3, removeCounters(2, Counter.CounterType.TIME, targetedBy(target), "remove two time counters from target permanent or suspended card."));
		}
	}
}
