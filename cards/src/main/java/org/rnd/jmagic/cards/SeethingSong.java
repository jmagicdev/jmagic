package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Seething Song")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SeethingSong extends Card
{
	public SeethingSong(GameState state)
	{
		super(state);
		this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)(R)(R)(R)"));
	}
}
