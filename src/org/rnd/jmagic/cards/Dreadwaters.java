package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dreadwaters")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Dreadwaters extends Card
{
	public Dreadwaters(GameState state)
	{
		super(state);

		// Target player puts the top X cards of his or her library into his or
		// her graveyard, where X is the number of lands you control.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator number = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
		this.addEffect(millCards(target, number, "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of lands you control."));
	}
}
