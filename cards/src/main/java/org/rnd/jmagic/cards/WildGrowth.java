package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wild Growth")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WildGrowth extends Card
{
	public static final class WildGrowthAbility1 extends EventTriggeredAbility
	{
		public WildGrowthAbility1(GameState state)
		{
			super(state, "Whenever enchanted land is tapped for mana, its controller adds (G) to his or her mana pool .");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(enchantedLand)));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("G")));
			parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedLand));
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Its controller adds (G) to his or her mana pool."));
		}
	}

	public WildGrowth(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Whenever enchanted land is tapped for mana, its controller adds (G)
		// to his or her mana pool (in addition to the mana the land produces).
		this.addAbility(new WildGrowthAbility1(state));
	}
}
