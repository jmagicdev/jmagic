package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Darksteel Sentinel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelSentinel extends Card
{
	public DarksteelSentinel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Darksteel Sentinel is indestructible. (Lethal damage and effects that
		// say "destroy" don't destroy it. If its toughness is 0 or less, it's
		// still put into its owner's graveyard.)
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));
	}
}
