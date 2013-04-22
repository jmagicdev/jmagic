package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Predator's Rapport")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PredatorsRapport extends Card
{
	public PredatorsRapport(GameState state)
	{
		super(state);

		// Choose target creature you control. You gain life equal to that
		// creature's power plus its toughness.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(gainLife(You.instance(), Sum.instance(Union.instance(PowerOf.instance(target), ToughnessOf.instance(target))), "Choose target creature you control. You gain life equal to that creature's power plus its toughness."));
	}
}
