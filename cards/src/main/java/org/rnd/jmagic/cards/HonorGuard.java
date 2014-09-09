package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Honor Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class HonorGuard extends Card
{
	public static final class BeefDefense extends ActivatedAbility
	{
		public BeefDefense(GameState state)
		{
			super(state, "(W): Honor Guard gets +0/+1 until end of turn.");

			this.setManaCost(new ManaPool("W"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+0), (+1), "Honor Guard gets +0/+1 until end of turn."));
		}
	}

	public HonorGuard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new BeefDefense(state));
	}
}
