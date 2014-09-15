package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyxborn Shieldmate")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class NyxbornShieldmate extends Card
{
	public static final class NyxbornShieldmateAbility1 extends StaticAbility
	{
		public NyxbornShieldmateAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +2));
		}
	}

	public NyxbornShieldmate(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Bestow (2)(W) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(2)(W)"));

		// Enchanted creature gets +1/+2.
		this.addAbility(new NyxbornShieldmateAbility1(state));
	}
}
