package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyxborn Wolf")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class NyxbornWolf extends Card
{
	public static final class NyxbornWolfAbility1 extends StaticAbility
	{
		public NyxbornWolfAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +3/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +1));
		}
	}

	public NyxbornWolf(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Bestow (4)(G) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(4)(G)"));

		// Enchanted creature gets +3/+1.
		this.addAbility(new NyxbornWolfAbility1(state));
	}
}
