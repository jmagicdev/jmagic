package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Furious Resistance")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FuriousResistance extends Card
{
	public FuriousResistance(GameState state)
	{
		super(state);

		// Target blocking creature gets +3/+0 and gains first strike until end
		// of turn.
		SetGenerator target = targetedBy(this.addTarget(Blocking.instance(), "target blocking creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +3, +0, "Target blocking creature gets +3/+0 and gains first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
