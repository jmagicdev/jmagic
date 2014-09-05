package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cryoclasm")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Coldsnap.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Cryoclasm extends Card
{
	public Cryoclasm(GameState state)
	{
		super(state);

		SetGenerator targetable = Intersect.instance(LandPermanents.instance(), Union.instance(HasSubType.instance(SubType.PLAINS), HasSubType.instance(SubType.ISLAND)));
		Target target = this.addTarget(targetable, "target Plains or Island");
		this.addEffect(destroy(targetedBy(target), "Destroy target Plains or Island."));
		this.addEffect(spellDealDamage(3, ControllerOf.instance(targetedBy(target)), "Cryoclasm deals 3 damage to that land's controller."));
	}
}
