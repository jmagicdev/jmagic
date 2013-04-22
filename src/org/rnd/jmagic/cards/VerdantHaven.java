package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Verdant Haven")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class VerdantHaven extends Card
{
	public static final class VerdantHavenAbility1 extends EventTriggeredAbility
	{
		public VerdantHavenAbility1(GameState state)
		{
			super(state, "When Verdant Haven enters the battlefield, you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public static final class VerdantHavenAbility2 extends EventTriggeredAbility
	{
		public VerdantHavenAbility2(GameState state)
		{
			super(state, "Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(enchantedLand)));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Its controller adds one mana of any color to his or her mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(WUBRG)")));
			addMana.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedLand));
			this.addEffect(addMana);
		}
	}

	public VerdantHaven(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// When Verdant Haven enters the battlefield, you gain 2 life.
		this.addAbility(new VerdantHavenAbility1(state));

		// Whenever enchanted land is tapped for mana, its controller adds one
		// mana of any color to his or her mana pool (in addition to the mana
		// the land produces).
		this.addAbility(new VerdantHavenAbility2(state));
	}
}
