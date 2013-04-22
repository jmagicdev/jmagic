package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Downpour")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Downpour extends Card
{
	public Downpour(GameState state)
	{
		super(state);

		// Tap up to three target creatures.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to three target creatures.");
		target.setNumber(0, 3);
		this.addEffect(tap(targetedBy(target), "Tap up to three target creatures."));
	}
}
