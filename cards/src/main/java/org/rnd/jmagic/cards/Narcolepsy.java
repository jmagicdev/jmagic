package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Narcolepsy")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Narcolepsy extends Card
{
	public static final class Sleepy extends EventTriggeredAbility
	{
		public Sleepy(GameState state)
		{
			super(state, "At the beginning of each upkeep, if enchanted creature is untapped, tap it.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator isAwake = Intersect.instance(enchantedCreature, Untapped.instance());
			this.interveningIf = isAwake;

			this.addEffect(tap(enchantedCreature, "Tap it."));
		}
	}

	public Narcolepsy(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// At the beginning of each upkeep, if enchanted creature is untapped,
		// tap it.
		this.addAbility(new Sleepy(state));
	}
}
