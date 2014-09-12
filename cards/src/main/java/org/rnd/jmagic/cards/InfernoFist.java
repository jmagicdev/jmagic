package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inferno Fist")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class InfernoFist extends Card
{
	public static final class InfernoFistAbility1 extends StaticAbility
	{
		public InfernoFistAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+0.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +0));
		}
	}

	public static final class InfernoFistAbility2 extends ActivatedAbility
	{
		public InfernoFistAbility2(GameState state)
		{
			super(state, "(R), Sacrifice Inferno Fist: Inferno Fist deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Inferno Fist"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Inferno Fist deals 2 damage to target creature or player."));
		}
	}

	public InfernoFist(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// Enchanted creature gets +2/+0.
		this.addAbility(new InfernoFistAbility1(state));

		// (R), Sacrifice Inferno Fist: Inferno Fist deals 2 damage to target
		// creature or player.
		this.addAbility(new InfernoFistAbility2(state));
	}
}
