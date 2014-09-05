package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gridlock")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Gridlock extends Card
{
	public Gridlock(GameState state)
	{
		super(state);

		// Tap X target nonland permanents.
		Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "X target nonland permanents");
		target.setSingleNumber(ValueOfX.instance(This.instance()));

		this.addEffect(tap(targetedBy(target), "Tap X target nonland permanents."));
	}
}
