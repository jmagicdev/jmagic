package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sewn-Eye Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.DRAKE})
@ManaCost("2(U/R)B")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class SewnEyeDrake extends Card
{
	public SewnEyeDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
