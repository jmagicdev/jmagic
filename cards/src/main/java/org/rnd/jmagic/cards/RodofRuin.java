package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rod of Ruin")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class RodofRuin extends Card
{
	public static final class RodOfRuinPing extends ActivatedAbility
	{
		public RodOfRuinPing(GameState state)
		{
			super(state, "(3), (T): Rod of Ruin deals 1 damage to target creature or player.");

			this.setManaCost(new ManaPool("3"));
			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Rod of Ruin deals 1 damage to target creature or player."));
		}
	}

	public RodofRuin(GameState state)
	{
		super(state);

		this.addAbility(new RodOfRuinPing(state));
	}
}
