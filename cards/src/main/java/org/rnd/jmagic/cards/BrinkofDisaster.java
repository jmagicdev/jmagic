package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Brink of Disaster")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BrinkofDisaster extends Card
{
	public static final class BrinkofDisasterAbility1 extends EventTriggeredAbility
	{
		public BrinkofDisasterAbility1(GameState state)
		{
			super(state, "When enchanted permanent becomes tapped, destroy it.");

			SetGenerator enchantedPermanent = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, enchantedPermanent);
			this.addPattern(pattern);

			this.addEffect(destroy(enchantedPermanent, "Destroy it"));
		}
	}

	public static final class EnchantCreatureOrLand extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		public EnchantCreatureOrLand(GameState state)
		{
			super(state, "creature or land", Union.instance(CreaturePermanents.instance(), LandPermanents.instance()));
		}
	}

	public BrinkofDisaster(GameState state)
	{
		super(state);

		// Enchant creature or land
		this.addAbility(new EnchantCreatureOrLand(state));

		// When enchanted permanent becomes tapped, destroy it.
		this.addAbility(new BrinkofDisasterAbility1(state));
	}
}
