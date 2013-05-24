package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pollenbright Wings")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4GW")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class PollenbrightWings extends Card
{
	public static final class PollenbrightWingsAbility1 extends StaticAbility
	{
		public PollenbrightWingsAbility1(GameState state)
		{
			super(state, "Enchanted creature has flying.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class DamageMakesBabies extends EventTriggeredAbility
	{
		public DamageMakesBabies(GameState state)
		{
			super(state, "Whenever enchanted creature deals combat damage to a player, put that many 1/1 green Saproling creature tokens onto the battlefield.");

			this.addPattern(whenDealsCombatDamageToAPlayer(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
			String effectName = "Put that many 1/1 green Saproling creature tokens onto the battlefield.";
			CreateTokensFactory tokens = new CreateTokensFactory(thatMany, numberGenerator(1), numberGenerator(1), effectName);
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.SAPROLING);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public PollenbrightWings(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has flying.
		this.addAbility(new PollenbrightWingsAbility1(state));

		// Whenever enchanted creature deals combat damage to a player, put that
		// many 1/1 green Saproling creature tokens onto the battlefield.
		this.addAbility(new DamageMakesBabies(state));
	}
}
