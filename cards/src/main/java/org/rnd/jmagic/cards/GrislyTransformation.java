package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grisly Transformation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class GrislyTransformation extends Card
{
	public static final class GrislyTransformationAbility1 extends EventTriggeredAbility
	{
		public GrislyTransformationAbility1(GameState state)
		{
			super(state, "When Grisly Transformation enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class GrislyTransformationAbility2 extends StaticAbility
	{
		public GrislyTransformationAbility2(GameState state)
		{
			super(state, "Enchanted creature has intimidate.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public GrislyTransformation(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Grisly Transformation enters the battlefield, draw a card.
		this.addAbility(new GrislyTransformationAbility1(state));

		// Enchanted creature has intimidate. (It can't be blocked except by
		// artifact creatures and/or creatures that share a color with it.)
		this.addAbility(new GrislyTransformationAbility2(state));
	}
}
