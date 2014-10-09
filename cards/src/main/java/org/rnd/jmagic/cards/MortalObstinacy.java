package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortal Obstinacy")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class MortalObstinacy extends Card
{
	public static final class MortalObstinacyAbility1 extends StaticAbility
	{
		public MortalObstinacyAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public static final class MortalObstinacyAbility2 extends EventTriggeredAbility
	{
		public MortalObstinacyAbility2(GameState state)
		{
			super(state, "Whenever enchanted creature deals combat damage to a player, you may sacrifice Mortal Obstinacy. If you do, destroy target enchantment.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenDealsCombatDamageToAPlayer(enchantedCreature));

			EventFactory sacrifice = sacrificeThis("Mortal Obstinacy");

			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			EventFactory destroy = destroy(target, "Destroy target enchantment");

			this.addEffect(ifThen(youMay(sacrifice), destroy, "You may sacrifice Mortal Obstinacy. If you do, destroy target enchantment."));
		}
	}

	public MortalObstinacy(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new MortalObstinacyAbility1(state));

		// Whenever enchanted creature deals combat damage to a player, you may
		// sacrifice Mortal Obstinacy. If you do, destroy target enchantment.
		this.addAbility(new MortalObstinacyAbility2(state));
	}
}
