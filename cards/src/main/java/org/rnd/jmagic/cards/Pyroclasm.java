package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pyroclasm")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Portal.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Pyroclasm extends Card
{
	public Pyroclasm(GameState state)
	{
		super(state);

		this.addEffect(spellDealDamage(2, CreaturePermanents.instance(), "Pyroclasm deals 2 damage to each creature."));
	}
}
