package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Epiphany Storm")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class EpiphanyStorm extends Card
{
	public static final class CycleStuff extends ActivatedAbility
	{
		public CycleStuff(GameState state)
		{
			super(state, "(R), (T), Discard a card: Draw a card.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));
			this.addEffect(drawACard());
		}
	}

	public static final class EpiphanyStormAbility1 extends StaticAbility
	{
		public EpiphanyStormAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(R), (T), Discard a card: Draw a card.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), CycleStuff.class));
		}
	}

	public EpiphanyStorm(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has "(R), (T), Discard a card: Draw a card."
		this.addAbility(new EpiphanyStormAbility1(state));
	}
}
