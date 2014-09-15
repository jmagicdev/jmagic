package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oracle's Insight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class OraclesInsight extends Card
{
	public static final class Insightful extends ActivatedAbility
	{
		public Insightful(GameState state)
		{
			super(state, "(T): Scry 1, then draw a card.");
			this.costsTap = true;
			this.addEffect(scry(1, "Scry 1,"));
			this.addEffect(drawCards(You.instance(), 1, "then draw a card."));
		}
	}

	public static final class OraclesInsightAbility1 extends StaticAbility
	{
		public OraclesInsightAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(T): Scry 1, then draw a card.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Insightful.class));
		}
	}

	public OraclesInsight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has "(T): Scry 1, then draw a card." (To scry 1,
		// look at the top card of your library, then you may put that card on
		// the bottom of your library.)
		this.addAbility(new OraclesInsightAbility1(state));
	}
}
