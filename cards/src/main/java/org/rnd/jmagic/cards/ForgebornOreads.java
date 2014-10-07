package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Forgeborn Oreads")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NYMPH})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class ForgebornOreads extends Card
{
	public static final class ForgebornOreadsAbility0 extends EventTriggeredAbility
	{
		public ForgebornOreadsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Forgeborn Oreads or another enchantment enters the battlefield under your control, Forgeborn Oreads deals 1 damage to target creature or player.");
			this.addPattern(constellation());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Forgeborn Oreads deals 1 damage to target creature or player."));
		}
	}

	public ForgebornOreads(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Constellation \u2014 Whenever Forgeborn Oreads or another enchantment
		// enters the battlefield under your control, Forgeborn Oreads deals 1
		// damage to target creature or player.
		this.addAbility(new ForgebornOreadsAbility0(state));
	}
}
