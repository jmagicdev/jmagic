package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Dragon Hatchling")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class DragonHatchling extends Card
{
	public static final class DragonHatchlingAbility1 extends ActivatedAbility
	{
		public DragonHatchlingAbility1(GameState state)
		{
			super(state, "(R): Dragon Hatchling gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+0), "+1/+0 until end of turn."));
		}
	}

	public DragonHatchling(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (R): Dragon Hatchling gets +1/+0 until end of turn.
		this.addAbility(new DragonHatchlingAbility1(state));
	}
}
