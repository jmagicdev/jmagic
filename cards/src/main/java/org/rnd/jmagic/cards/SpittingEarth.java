package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spitting Earth")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SpittingEarth extends Card
{
	public SpittingEarth(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator amount = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN)));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Spitting Earth deals damage to target creature equal to the number of Mountains you control."));
	}
}
