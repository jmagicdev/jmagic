package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gnarled Scarhide")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.MINOTAUR})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class GnarledScarhide extends Card
{
	public static final class GnarledScarhideAbility2 extends StaticAbility
	{
		public GnarledScarhideAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +2/+1 and can't block.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +1));

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), EnchantedBy.instance(This.instance()))));
			this.addEffectPart(part2);
		}
	}

	public GnarledScarhide(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Bestow (3)(B) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(3)(B)"));

		// Gnarled Scarhide can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Enchanted creature gets +2/+1 and can't block.
		this.addAbility(new GnarledScarhideAbility2(state));
	}
}
