package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cower in Fear")
@Types({Type.INSTANT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CowerinFear extends Card
{
	public CowerinFear(GameState state)
	{
		super(state);

		// Creatures your opponents control get -1/-1 until end of turn.
		SetGenerator creaturesYourOpponentsControl = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
		this.addEffect(ptChangeUntilEndOfTurn(creaturesYourOpponentsControl, (-1), (-1), "Creatures your opponents control get -1/-1 until end of turn."));
	}
}
