package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Boggart Ram-Gang")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("(RG)(RG)(RG)")
@ColorIdentity({Color.RED, Color.GREEN})
public final class BoggartRamGang extends Card
{
	public BoggartRamGang(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Wither(state));
	}
}
