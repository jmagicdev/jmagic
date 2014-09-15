package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyxborn Rollicker")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SATYR})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class NyxbornRollicker extends Card
{
	public static final class NyxbornRollickerAbility1 extends StaticAbility
	{
		public NyxbornRollickerAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public NyxbornRollicker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (1)(R) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(1)(R)"));

		// Enchanted creature gets +1/+1.
		this.addAbility(new NyxbornRollickerAbility1(state));
	}
}
