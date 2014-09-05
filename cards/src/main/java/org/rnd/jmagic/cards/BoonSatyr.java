package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boon Satyr")
@Types({Type.CREATURE,Type.ENCHANTMENT})
@SubTypes({SubType.SATYR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BoonSatyr extends Card
{
	public static final class BoonSatyrAbility2 extends StaticAbility
	{
		public BoonSatyrAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +4/+2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +4, +2));
		}
	}

	public BoonSatyr(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Bestow (3)(G)(G) (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(3)(G)(G)"));

		// Enchanted creature gets +4/+2.
		this.addAbility(new BoonSatyrAbility2(state));
	}
}
