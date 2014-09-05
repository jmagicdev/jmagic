package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Guardians' Pledge")
@Types({Type.INSTANT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GuardiansPledge extends Card
{
	public GuardiansPledge(GameState state)
	{
		super(state);

		// White creatures you control get +2/+2 until end of turn.
		SetGenerator affect = Intersect.instance(HasColor.instance(Color.WHITE), CREATURES_YOU_CONTROL);
		this.addEffect(ptChangeUntilEndOfTurn(affect, +2, +2, "White creatures you control get +2/+2 until end of turn."));
	}
}
