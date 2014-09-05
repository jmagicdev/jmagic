package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Last Gasp")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LastGasp extends Card
{
	public LastGasp(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// Target creature gets -3/-3 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-3), (-3), "Target creature gets -3/-3 until end of turn."));
	}
}
