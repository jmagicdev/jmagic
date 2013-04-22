package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dark Favor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DarkFavor extends Card
{
	public static final class DarkFavorAbility1 extends EventTriggeredAbility
	{
		public DarkFavorAbility1(GameState state)
		{
			super(state, "When Dark Favor enters the battlefield, you lose 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(loseLife(You.instance(), 1, "You lose 1 life."));
		}
	}

	public static final class DarkFavorAbility2 extends StaticAbility
	{
		public DarkFavorAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +3/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +1));
		}
	}

	public DarkFavor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Dark Favor enters the battlefield, you lose 1 life.
		this.addAbility(new DarkFavorAbility1(state));

		// Enchanted creature gets +3/+1.
		this.addAbility(new DarkFavorAbility2(state));
	}
}
