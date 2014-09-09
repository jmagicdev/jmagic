package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Dark Ritual")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class DarkRitual extends Card
{
	public DarkRitual(GameState state)
	{
		super(state);

		this.addEffect(addManaToYourManaPoolFromSpell("(B)(B)(B)"));
	}
}
