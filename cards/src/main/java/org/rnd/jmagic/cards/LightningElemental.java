package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lightning Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class LightningElemental extends Card
{
	public LightningElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
