package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lust for War")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class LustforWar extends Card
{
	public static final class PainfulLust extends EventTriggeredAbility
	{
		public PainfulLust(GameState state)
		{
			super(state, "Whenever enchanted creature becomes tapped, Lust for War deals 3 damage to that creature's controller.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			SimpleEventPattern tapped = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tapped.put(EventType.Parameter.OBJECT, enchantedLand);

			this.addEffect(permanentDealDamage(3, ControllerOf.instance(enchantedLand), "Lust for War deals 3 damage to that creature's controller."));
		}
	}

	public static final class AttacksEachTurnIfAble extends StaticAbility
	{
		public AttacksEachTurnIfAble(GameState state)
		{
			super(state, "Enchanted creature attacks each turn if able.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, EnchantedBy.instance(This.instance()));
			this.addEffectPart(part);
		}
	}

	public LustforWar(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature becomes tapped, Lust for War deals 3
		// damage to that creature's controller.
		this.addAbility(new PainfulLust(state));

		// Enchanted creature attacks each turn if able.
		this.addAbility(new AttacksEachTurnIfAble(state));
	}
}
