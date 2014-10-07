package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cast into Darkness")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class CastintoDarkness extends Card
{
	public static final class CastintoDarknessAbility1 extends StaticAbility
	{
		public CastintoDarknessAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -2/-0 and can't block.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, -2, -0));

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), enchantedCreature)));
			this.addEffectPart(part2);
		}
	}

	public CastintoDarkness(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -2/-0 and can't block.
		this.addAbility(new CastintoDarknessAbility1(state));
	}
}
