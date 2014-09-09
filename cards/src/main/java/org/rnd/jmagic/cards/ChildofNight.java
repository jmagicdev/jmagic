package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Child of Night")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class ChildofNight extends Card
{
	public ChildofNight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
