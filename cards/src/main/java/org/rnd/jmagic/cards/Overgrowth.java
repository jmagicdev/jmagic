package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Overgrowth")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Overgrowth extends Card
{
	// Whenever enchanted land is tapped for mana, its controller adds GG to his
	// or her mana pool (in addition to the mana the land produces).
	public static final class Overgrow extends EventTriggeredAbility
	{
		public Overgrow(GameState state)
		{
			super(state, "Whenever enchanted land is tapped for mana, its controller adds (G)(G) to his or her mana pool.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(enchantedLand)));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("GG")));
			parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedLand));
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Its controller adds GG to his or her mana pool."));
		}
	}

	public Overgrowth(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));
		this.addAbility(new Overgrow(state));
	}
}
