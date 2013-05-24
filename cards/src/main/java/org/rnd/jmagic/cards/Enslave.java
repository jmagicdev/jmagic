package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Enslave")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Enslave extends Card
{
	public static final class EnslaveAbility2 extends EventTriggeredAbility
	{
		public EnslaveAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, enchanted creature deals 1 damage to its owner.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory factory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Enchanted creature deals 1 damage to its owner.");
			factory.parameters.put(EventType.Parameter.SOURCE, enchantedCreature);
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TAKER, OwnerOf.instance(enchantedCreature));
			this.addEffect(factory);
		}
	}

	public Enslave(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));

		// At the beginning of your upkeep, enchanted creature deals 1 damage to
		// its owner.
		this.addAbility(new EnslaveAbility2(state));
	}
}
