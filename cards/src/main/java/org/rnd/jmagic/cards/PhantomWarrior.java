package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Phantom Warrior")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION, SubType.WARRIOR})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Weatherlight.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Portal.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PhantomWarrior extends Card
{
	public PhantomWarrior(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, "Phantom Warrior"));
	}
}
