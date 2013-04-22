package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chant of the Skifsang")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ChantoftheSkifsang extends Card
{
	public static final class ChantoftheSkifsangAbility1 extends StaticAbility
	{
		public ChantoftheSkifsangAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -13/-0.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -13, -0));
		}
	}

	public ChantoftheSkifsang(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -13/-0.
		this.addAbility(new ChantoftheSkifsangAbility1(state));
	}
}
