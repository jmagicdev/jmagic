package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Warclamp Mastiff")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WarclampMastiff extends Card
{
	public WarclampMastiff(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
