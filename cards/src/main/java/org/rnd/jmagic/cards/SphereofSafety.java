package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphere of Safety")
@Types({Type.ENCHANTMENT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SphereofSafety extends Card
{
	public static final class SphereofSafetyAbility0 extends StaticAbility
	{
		public SphereofSafetyAbility0(GameState state)
		{
			super(state, "Creatures can't attack you or a planeswalker you control unless their controller pays (X) for each of those creatures, where X is the number of enchantments you control.");

			SetGenerator yourEnchantments = Intersect.instance(EnchantmentPermanents.instance(), ControlledBy.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(yourEnchantments));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Union.instance(You.instance(), Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(You.instance()))));
			this.addEffectPart(part);
		}
	}

	public SphereofSafety(GameState state)
	{
		super(state);

		// Creatures can't attack you or a planeswalker you control unless their
		// controller pays (X) for each of those creatures, where X is the
		// number of enchantments you control.
		this.addAbility(new SphereofSafetyAbility0(state));
	}
}
