package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snapping Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class SnappingDrake extends Card
{
	public SnappingDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
