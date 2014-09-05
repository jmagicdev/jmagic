package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pistus Strike")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PistusStrike extends Card
{
	public PistusStrike(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying"));

		// Destroy target creature with flying. Its controller gets a poison
		// counter.
		this.addEffect(destroy(target, "Destroy target creature with flying."));
		this.addEffect(putCounters(1, Counter.CounterType.POISON, ControllerOf.instance(target), "Its controller gets a poison counter."));
	}
}
