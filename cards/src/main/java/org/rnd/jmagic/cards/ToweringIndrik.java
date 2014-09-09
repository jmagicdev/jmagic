package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Towering Indrik")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class ToweringIndrik extends Card
{
	public ToweringIndrik(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
