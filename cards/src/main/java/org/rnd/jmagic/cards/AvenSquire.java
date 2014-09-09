package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Aven Squire")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AvenSquire extends Card
{
	public AvenSquire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
