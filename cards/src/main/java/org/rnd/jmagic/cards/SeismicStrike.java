package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seismic Strike")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SeismicStrike extends Card
{
	public SeismicStrike(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator amount = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN)));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Seismic Strike deals damage to target creature equal to the number of Mountains you control."));
	}
}
