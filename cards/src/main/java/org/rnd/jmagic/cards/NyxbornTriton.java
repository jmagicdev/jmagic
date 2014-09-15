package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyxborn Triton")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class NyxbornTriton extends Card
{
	public static final class NyxbornTritonAbility1 extends StaticAbility
	{
		public NyxbornTritonAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+3.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +3));
		}
	}

	public NyxbornTriton(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Bestow (4)(U) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(4)(U)"));

		// Enchanted creature gets +2/+3.
		this.addAbility(new NyxbornTritonAbility1(state));
	}
}
