package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spirespine")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class Spirespine extends Card
{
	public static final class SpirespineAbility1 extends StaticAbility
	{
		public SpirespineAbility1(GameState state)
		{
			super(state, "Spirespine blocks each turn if able.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, This.instance());
			this.addEffectPart(part);
		}
	}

	public static final class SpirespineAbility2 extends StaticAbility
	{
		public SpirespineAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +4/+1 and blocks each turn if able.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +4, +1));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, EnchantedBy.instance(This.instance()));
			this.addEffectPart(part);
		}
	}

	public Spirespine(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Bestow (4)(G) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(4)(G)"));

		// Spirespine blocks each turn if able.
		this.addAbility(new SpirespineAbility1(state));

		// Enchanted creature gets +4/+1 and blocks each turn if able.
		this.addAbility(new SpirespineAbility2(state));
	}
}
