package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Seething Song")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class SeethingSong extends Card
{
	public SeethingSong(GameState state)
	{
		super(state);
		this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)(R)(R)(R)"));
	}
}
