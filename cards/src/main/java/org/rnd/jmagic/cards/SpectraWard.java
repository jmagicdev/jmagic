package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Spectra Ward")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class SpectraWard extends Card
{
	public static final class ColorProtection extends org.rnd.jmagic.abilities.keywords.Protection
	{
		public static final class SpectraStatic extends ProtectionStatic
		{
			public SpectraStatic(GameState state)
			{
				super(state, new SimpleSetPattern(HasColor.instance(Color.allColors())), new SubTypePattern(SubType.AURA), "all colors");
			}
		}

		public ColorProtection(GameState state)
		{
			super(state, "all colors");
		}

		@Override
		protected org.rnd.jmagic.abilities.keywords.Protection.ProtectionStatic getProtectionStatic()
		{
			return new SpectraStatic(this.state);
		}
	}

	public static final class SpectraWardAbility1 extends StaticAbility
	{
		public SpectraWardAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has protection from all colors. This effect doesn't remove Auras.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), ColorProtection.class));
		}
	}

	public SpectraWard(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has protection from all colors.
		// This effect doesn't remove Auras. (It can't be blocked, targeted, or
		// dealt damage by anything that's white, blue, black, red, or green.)
		this.addAbility(new SpectraWardAbility1(state));
	}
}
