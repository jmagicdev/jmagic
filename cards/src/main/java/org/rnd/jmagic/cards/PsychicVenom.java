package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Psychic Venom")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class PsychicVenom extends Card
{
	public static final class TapForDeath extends EventTriggeredAbility
	{
		public TapForDeath(GameState state)
		{
			super(state, "Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern tapped = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tapped.put(EventType.Parameter.OBJECT, enchantedLand);
			this.addPattern(tapped);

			this.addEffect(permanentDealDamage(2, ControllerOf.instance(enchantedLand), "Psychic Venom deals 2 damage to that land's controller."));
		}
	}

	public PsychicVenom(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage
		// to that land's controller.
		this.addAbility(new TapForDeath(state));
	}
}
