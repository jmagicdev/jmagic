package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Moonveil Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RRR")
@ColorIdentity({Color.RED})
public final class MoonveilDragon extends Card
{
	public static final class MoonveilDragonAbility1 extends ActivatedAbility
	{
		public MoonveilDragonAbility1(GameState state)
		{
			super(state, "(R): Each creature you control gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +0, "Each creature you control gets +1/+0 until end of turn."));
		}
	}

	public MoonveilDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (R): Each creature you control gets +1/+0 until end of turn.
		this.addAbility(new MoonveilDragonAbility1(state));
	}
}
