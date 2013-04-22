package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banewasp Affliction")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BanewaspAffliction extends Card
{
	public static final class BanewaspAfflictionAbility1 extends EventTriggeredAbility
	{
		public BanewaspAfflictionAbility1(GameState state)
		{
			super(state, "When enchanted creature dies, that creature's controller loses life equal to its toughness.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			this.addPattern(whenXDies(enchantedCreature));

			this.addEffect(loseLife(ControllerOf.instance(enchantedCreature), ToughnessOf.instance(enchantedCreature), "That creature's controller loses life equal to its toughness."));
		}
	}

	public BanewaspAffliction(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When enchanted creature is put into a graveyard, that creature's
		// controller loses life equal to its toughness.
		this.addAbility(new BanewaspAfflictionAbility1(state));
	}
}
