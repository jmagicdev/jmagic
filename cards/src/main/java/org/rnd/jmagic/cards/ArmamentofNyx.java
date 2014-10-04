package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armament of Nyx")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class ArmamentofNyx extends Card
{
	public static final class ArmamentofNyxAbility1 extends StaticAbility
	{
		public ArmamentofNyxAbility1(GameState state)
		{
			super(state, "Enchanted creature has double strike as long as it's an enchantment. Otherwise, prevent all damage that would be dealt by enchanted creature.");

			SetGenerator itsAnEnchantment = Intersect.instance(EnchantedBy.instance(This.instance()), EnchantmentPermanents.instance());
			SetGenerator doubleStrike = IfThenElse.instance(itsAnEnchantment, Identity.instance(new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.DoubleStrike.class)), Empty.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, doubleStrike);
			this.addEffectPart(part);

			SetGenerator notEnchantment = RelativeComplement.instance(EnchantedBy.instance(This.instance()), EnchantmentPermanents.instance());
			DamageReplacementEffect prevent = new org.rnd.jmagic.abilities.PreventAllFrom(state.game, notEnchantment, "enchanted creature");
			this.addEffectPart(replacementEffectPart(prevent));
		}
	}

	public ArmamentofNyx(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has double strike as long as it's an enchantment.
		// Otherwise, prevent all damage that would be dealt by enchanted
		// creature. (A creature with double strike deals both first-strike and
		// regular combat damage.)
		this.addAbility(new ArmamentofNyxAbility1(state));
	}
}
