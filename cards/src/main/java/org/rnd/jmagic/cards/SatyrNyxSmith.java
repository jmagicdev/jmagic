package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Satyr Nyx-Smith")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR, SubType.SHAMAN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class SatyrNyxSmith extends Card
{
	public static final class SatyrNyxSmithAbility1 extends EventTriggeredAbility
	{
		public SatyrNyxSmithAbility1(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Satyr Nyx-Smith becomes untapped, you may pay (2)(R). If you do, put a 3/1 red Elemental enchantment creature token with haste onto the battlefield.");
			this.addPattern(inspired());

			EventFactory mayPay = youMayPay("(2)(R)");

			CreateTokensFactory elemental = new CreateTokensFactory(1, 3, 1, "Put a 3/1 red Elemental enchantment creature token with haste onto the battlefield.");
			elemental.setColors(Color.RED);
			elemental.setSubTypes(SubType.ELEMENTAL);
			elemental.setEnchantment();
			elemental.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(ifThen(mayPay, elemental.getEventFactory(), "You may pay (2)(R). If you do, put a 3/1 red Elemental enchantment creature token with haste onto the battlefield."));
		}
	}

	public SatyrNyxSmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Inspired \u2014 Whenever Satyr Nyx-Smith becomes untapped, you may
		// pay (2)(R). If you do, put a 3/1 red Elemental enchantment creature
		// token with haste onto the battlefield.
		this.addAbility(new SatyrNyxSmithAbility1(state));
	}
}
