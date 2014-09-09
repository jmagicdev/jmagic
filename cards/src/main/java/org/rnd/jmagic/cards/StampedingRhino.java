package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stampeding Rhino")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class StampedingRhino extends Card
{
	public StampedingRhino(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
