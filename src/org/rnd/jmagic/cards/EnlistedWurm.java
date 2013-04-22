package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Enlisted Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GW")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class EnlistedWurm extends Card
{
	public EnlistedWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));
	}
}
