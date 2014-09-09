package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Malicious Intent")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class MaliciousIntent extends Card
{
	public static final class Intent extends ActivatedAbility
	{
		public Intent(GameState state)
		{
			super(state, "(T): Target creature can't block this turn.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator restriction = Intersect.instance(Blocking.instance(), target);

			ContinuousEffect.Part cantBlock = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			cantBlock.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", cantBlock));
		}
	}

	public static final class MaliciousIntentAbility1 extends StaticAbility
	{
		public MaliciousIntentAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(T): Target creature can't block this turn.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Intent.class));
		}
	}

	public MaliciousIntent(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has "(T): Target creature can't block this turn."
		this.addAbility(new MaliciousIntentAbility1(state));
	}
}
