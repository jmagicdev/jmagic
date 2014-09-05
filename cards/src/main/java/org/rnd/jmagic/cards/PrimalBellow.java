package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Primal Bellow")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class PrimalBellow extends Card
{
	public PrimalBellow(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 until end of turn for each Forest you
		// control.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator yourForests = Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance()));
		SetGenerator boostAmount = Count.instance(yourForests);

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), boostAmount, boostAmount, "Target creature gets +1/+1 until end of turn for each Forest you control."));
	}
}
