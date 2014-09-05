package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Barter in Blood")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BarterinBlood extends Card
{
	public BarterinBlood(GameState state)
	{
		super(state);

		// Each player sacrifices two creatures.
		this.addEffect(sacrifice(Players.instance(), 2, CreaturePermanents.instance(), "Each player sacrifices two creatures."));
	}
}
