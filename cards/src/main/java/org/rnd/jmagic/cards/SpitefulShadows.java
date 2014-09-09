package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spiteful Shadows")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class SpitefulShadows extends Card
{
	public static final class SpitefulShadowsAbility1 extends EventTriggeredAbility
	{
		public SpitefulShadowsAbility1(GameState state)
		{
			super(state, "Whenever enchanted creature is dealt damage, it deals that much damage to its controller.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenIsDealtDamage(enchanted));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatMuch = Count.instance(triggerDamage);

			this.addEffect(permanentDealDamage(thatMuch, ControllerOf.instance(enchanted), "It deals that much damage to its controller."));
		}
	}

	public SpitefulShadows(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature is dealt damage, it deals that much
		// damage to its controller.
		this.addAbility(new SpitefulShadowsAbility1(state));
	}
}
