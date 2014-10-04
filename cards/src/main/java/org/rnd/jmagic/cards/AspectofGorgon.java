package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aspect of Gorgon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class AspectofGorgon extends Card
{
	public static final class AspectofGorgonAbility1 extends StaticAbility
	{
		public AspectofGorgonAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+3 and has deathtouch.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +3));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public AspectofGorgon(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+3 and has deathtouch. (Any amount of
		// damage it deals to a creature is enough to destroy it.)
		this.addAbility(new AspectofGorgonAbility1(state));
	}
}
