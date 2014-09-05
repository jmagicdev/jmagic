package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Predator's Strike")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PredatorsStrike extends Card
{
	public PredatorsStrike(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// Target creature gets +3/+3 and gains trample until end of turn.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +3, +3, "Target creature gets +3/+3 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
