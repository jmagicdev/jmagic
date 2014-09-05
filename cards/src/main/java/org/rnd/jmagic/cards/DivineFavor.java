package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Divine Favor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class DivineFavor extends Card
{
	public static final class DivineFavorAbility1 extends EventTriggeredAbility
	{
		public DivineFavorAbility1(GameState state)
		{
			super(state, "When Divine Favor enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public static final class DivineFavorAbility2 extends StaticAbility
	{
		public DivineFavorAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+3.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +3));
		}
	}

	public DivineFavor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Divine Favor enters the battlefield, you gain 3 life.
		this.addAbility(new DivineFavorAbility1(state));

		// Enchanted creature gets +1/+3.
		this.addAbility(new DivineFavorAbility2(state));
	}
}
