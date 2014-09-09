package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mogg Fanatic")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class MoggFanatic extends Card
{
	public static final class SacPing extends ActivatedAbility
	{
		public SacPing(GameState state)
		{
			super(state, "Sacrifice Mogg Fanatic: Mogg Fanatic deals 1 damage to target creature or player.");

			// costs
			this.addCost(sacrificeThis("Mogg Fanatic"));

			// targets
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			// effects
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Mogg Fanatic deals 1 damage to target creature or player."));
		}
	}

	public MoggFanatic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new SacPing(state));
	}
}
