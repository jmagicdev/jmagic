package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lightning Diadem")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class LightningDiadem extends Card
{
	public static final class LightningDiademAbility1 extends EventTriggeredAbility
	{
		public LightningDiademAbility1(GameState state)
		{
			super(state, "When Lightning Diadem enters the battlefield, it deals 2 damage to target creature or player.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Lightning Diadem deals 2 damage to target creature or player."));
		}
	}

	public static final class LightningDiademAbility2 extends StaticAbility
	{
		public LightningDiademAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
		}
	}

	public LightningDiadem(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Lightning Diadem enters the battlefield, it deals 2 damage to
		// target creature or player.
		this.addAbility(new LightningDiademAbility1(state));

		// Enchanted creature gets +2/+2.
		this.addAbility(new LightningDiademAbility2(state));
	}
}
