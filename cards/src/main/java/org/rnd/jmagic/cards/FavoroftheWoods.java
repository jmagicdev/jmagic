package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Favor of the Woods")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class FavoroftheWoods extends Card
{
	public static final class FavoroftheWoodsAbility1 extends EventTriggeredAbility
	{
		public FavoroftheWoodsAbility1(GameState state)
		{
			super(state, "Whenever enchanted creature blocks, you gain 3 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.OBJECT, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public FavoroftheWoods(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature blocks, you gain 3 life.
		this.addAbility(new FavoroftheWoodsAbility1(state));
	}
}
