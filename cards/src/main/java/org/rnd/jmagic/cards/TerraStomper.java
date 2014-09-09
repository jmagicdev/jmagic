package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Terra Stomper")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GGG")
@ColorIdentity({Color.GREEN})
public final class TerraStomper extends Card
{
	public TerraStomper(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, "Terra Stomper"));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
