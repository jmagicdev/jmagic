package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mogis's Warhound")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class MogissWarhound extends Card
{
	public static final class MogissWarhoundAbility2 extends StaticAbility
	{
		public MogissWarhoundAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and attacks each turn if able.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, EnchantedBy.instance(This.instance()));
			this.addEffectPart(part);
		}
	}

	public MogissWarhound(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Bestow (2)(R) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(2)(R)"));

		// Mogis's Warhound attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));

		// Enchanted creature gets +2/+2 and attacks each turn if able.
		this.addAbility(new MogissWarhoundAbility2(state));
	}
}
