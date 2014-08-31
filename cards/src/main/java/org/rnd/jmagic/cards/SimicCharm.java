package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Simic Charm")
@Types({Type.INSTANT})
@ManaCost("GU")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicCharm extends Card
{
	public SimicCharm(GameState state)
	{
		super(state);

		// Choose one \u2014 Target creature gets +3/+3 until end of turn; or
		// permanents you control gain hexproof until end of turn; or return
		// target creature to its owner's hand.
		SetGenerator target1 = targetedBy(this.addTarget(1, CreaturePermanents.instance(), "target creature"));
		this.addEffect(1, ptChangeUntilEndOfTurn(target1, +3, +3, "Target creature gets +3/+3 until end of turn."));

		SetGenerator permanentsYouControl = Intersect.instance(Permanents.instance(), ControlledBy.instance(You.instance()));
		this.addEffect(2, addAbilityUntilEndOfTurn(permanentsYouControl, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Permanents you control gain hexproof until end of turn."));

		SetGenerator target3 = targetedBy(this.addTarget(3, CreaturePermanents.instance(), "target creature"));
		this.addEffect(3, bounce(target3, "Return target creature to its owner's hand."));
	}
}
