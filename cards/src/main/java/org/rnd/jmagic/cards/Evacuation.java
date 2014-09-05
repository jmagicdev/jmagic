package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Evacuation")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Stronghold.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Evacuation extends Card
{
	public Evacuation(GameState state)
	{
		super(state);

		this.addEffect(bounce(CreaturePermanents.instance(), "Return all creatures to their owners' hands."));
	}
}
