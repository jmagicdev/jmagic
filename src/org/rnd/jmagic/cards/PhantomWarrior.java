package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phantom Warrior")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION, SubType.WARRIOR})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.RARE)})
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
