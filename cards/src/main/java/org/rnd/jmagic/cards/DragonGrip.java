package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dragon Grip")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class DragonGrip extends Card
{
	public static final class DragonGripAbility0 extends StaticAbility
	{
		public DragonGripAbility0(GameState state)
		{
			super(state, "Ferocious \u2014 If you control a creature with power 4 or greater, you may cast Dragon Grip as though it had flash.");

			this.canApply = Ferocious.instance();

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
			this.addEffectPart(part);
		}
	}

	public static final class DragonGripAbility2 extends StaticAbility
	{
		public DragonGripAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +2/+0 and has first strike.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +0));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public DragonGrip(GameState state)
	{
		super(state);

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// you may cast Dragon Grip as though it had flash. (You may cast it any
		// time you could cast an instant.)
		this.addAbility(new DragonGripAbility0(state));

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+0 and has first strike.
		this.addAbility(new DragonGripAbility2(state));
	}
}
