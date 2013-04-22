package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Domestication")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Domestication extends Card
{
	public static final class CantBeTooBig extends EventTriggeredAbility
	{
		public CantBeTooBig(GameState state)
		{
			super(state, "At the beginning of your end step, if enchanted creature's power is 4 or greater, sacrifice Domestication.");
			this.addPattern(atTheBeginningOfYourEndStep());

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator tooBig = Intersect.instance(PowerOf.instance(enchantedCreature), Between.instance(4, null));
			this.interveningIf = tooBig;

			this.addEffect(sacrificeThis("Domestication"));
		}
	}

	public Domestication(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));

		// At the beginning of your end step, if enchanted creature's power is 4
		// or greater, sacrifice Domestication.
		this.addAbility(new CantBeTooBig(state));
	}
}
