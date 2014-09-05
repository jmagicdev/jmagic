package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Unnatural Predation")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class UnnaturalPredation extends Card
{
	public UnnaturalPredation(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains trample until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +1, +1, "Target creature gets +1/+1 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
