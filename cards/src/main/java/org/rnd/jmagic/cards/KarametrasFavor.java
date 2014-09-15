package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Karametra's Favor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class KarametrasFavor extends Card
{
	public static final class KarametrasFavorAbility1 extends EventTriggeredAbility
	{
		public KarametrasFavorAbility1(GameState state)
		{
			super(state, "When Karametra's Favor enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class KarametrasFavorAbility2 extends StaticAbility
	{
		public KarametrasFavorAbility2(GameState state)
		{
			super(state, "Enchanted creature has \"(T): Add one mana of any color to your mana pool.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.TapForAnyColor.class));
		}
	}

	public KarametrasFavor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Karametra's Favor enters the battlefield, draw a card.
		this.addAbility(new KarametrasFavorAbility1(state));

		// Enchanted creature has
		// "(T): Add one mana of any color to your mana pool."
		this.addAbility(new KarametrasFavorAbility2(state));
	}
}
