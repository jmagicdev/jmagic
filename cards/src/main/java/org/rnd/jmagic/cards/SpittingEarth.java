package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spitting Earth")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.COMMON)})
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
