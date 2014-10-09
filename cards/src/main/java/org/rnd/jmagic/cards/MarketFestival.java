package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Market Festival")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class MarketFestival extends Card
{
	public static final class MarketFestivalAbility1 extends EventTriggeredAbility
	{
		public MarketFestivalAbility1(GameState state)
		{
			super(state, "Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors to his or her mana pool.");
			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(EnchantedBy.instance(This.instance()))));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)(WUBRG)"));
		}
	}

	public MarketFestival(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Whenever enchanted land is tapped for mana, its controller adds two
		// mana in any combination of colors to his or her mana pool (in
		// addition to the mana the land produces).
		this.addAbility(new MarketFestivalAbility1(state));
	}
}
