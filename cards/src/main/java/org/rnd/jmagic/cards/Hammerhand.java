package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hammerhand")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Hammerhand extends Card
{
	public static final class HammerhandAbility1 extends EventTriggeredAbility
	{
		public HammerhandAbility1(GameState state)
		{
			super(state, "When Hammerhand enters the battlefield, target creature can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public static final class HammerhandAbility2 extends StaticAbility
	{
		public HammerhandAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has haste.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public Hammerhand(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Hammerhand enters the battlefield, target creature can't block
		// this turn.
		this.addAbility(new HammerhandAbility1(state));

		// Enchanted creature gets +1/+1 and has haste. (It can attack and (T)
		// no matter when it came under your control.)
		this.addAbility(new HammerhandAbility2(state));
	}
}
