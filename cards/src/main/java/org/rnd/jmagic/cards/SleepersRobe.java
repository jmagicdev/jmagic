package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sleeper's Robe")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SleepersRobe extends Card
{
	public static final class SleepersRobeAbility1 extends StaticAbility
	{
		public SleepersRobeAbility1(GameState state)
		{
			super(state, "Enchanted creature has fear.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Fear.class));
		}
	}

	public static final class Steal extends EventTriggeredAbility
	{
		public Steal(GameState state)
		{
			super(state, "Whenever enchanted creature deals combat damage to an opponent, you may draw a card.");

			this.addPattern(whenDealsCombatDamageToAnOpponent(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			EventFactory draw = drawACard();
			this.addEffect(youMay(draw, "You may draw a card."));
		}
	}

	public SleepersRobe(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has fear.
		this.addAbility(new SleepersRobeAbility1(state));

		// Whenever enchanted creature deals combat damage to an opponent, you
		// may draw a card.
		this.addAbility(new Steal(state));
	}
}
