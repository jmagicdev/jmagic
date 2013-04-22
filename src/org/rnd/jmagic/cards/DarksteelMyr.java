package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Darksteel Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelMyr extends Card
{
	public DarksteelMyr(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Darksteel Myr is indestructible. (Lethal damage and effects that say
		// "destroy" don't destroy it. If its toughness is 0 or less, it's still
		// put into its owner's graveyard.)
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));
	}
}
