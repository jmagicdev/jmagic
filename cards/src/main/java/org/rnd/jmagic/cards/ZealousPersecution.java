package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Zealous Persecution")
@Types({Type.INSTANT})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ZealousPersecution extends Card
{
	public ZealousPersecution(GameState state)
	{
		super(state);

		// Until end of turn, creatures you control get +1/+1
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Until end of turn, creatures you control get +1/+1"));

		// and creatures your opponents control get -1/-1.
		this.addEffect(ptChangeUntilEndOfTurn(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), -1, -1, "and creatures your opponents control get -1/-1."));
	}
}
