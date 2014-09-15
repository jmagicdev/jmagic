package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Noble Quarry")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.UNICORN})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class NobleQuarry extends Card
{
	public static final class NobleQuarryAbility1 extends StaticAbility
	{
		public NobleQuarryAbility1(GameState state)
		{
			super(state, "All creatures able to block Noble Quarry or enchanted creature do so.");

			SetGenerator thisIsAura = Intersect.instance(This.instance(), HasSubType.instance(SubType.AURA));
			SetGenerator toBeBlocked = IfThenElse.instance(thisIsAura, EnchantedBy.instance(This.instance()), This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, toBeBlocked);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class NobleQuarryAbility2 extends StaticAbility
	{
		public NobleQuarryAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public NobleQuarry(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (5)(G) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(5)(G)"));

		// All creatures able to block Noble Quarry or enchanted creature do so.
		this.addAbility(new NobleQuarryAbility1(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new NobleQuarryAbility2(state));
	}
}
