package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fearsome Temper")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class FearsomeTemper extends Card
{
	public static final class StunThings extends ActivatedAbility
	{
		public StunThings(GameState state)
		{
			super(state, "(2)(R): Target creature can't block this creature this turn.");
			this.setManaCost(new ManaPool("(2)(R)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), target)));
			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public static final class FearsomeTemperAbility1 extends StaticAbility
	{
		public FearsomeTemperAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"(2)(R): Target creature can't block this creature this turn.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), StunThings.class));
		}
	}

	public FearsomeTemper(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has
		// "(2)(R): Target creature can't block this creature this turn."
		this.addAbility(new FearsomeTemperAbility1(state));
	}
}
