package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Aerie Worshippers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AerieWorshippers extends Card
{
	public static final class AerieWorshippersAbility0 extends EventTriggeredAbility
	{
		public AerieWorshippersAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Aerie Worshippers becomes untapped, you may pay (2)(U). If you do, put a 2/2 blue Bird enchantment creature token with flying onto the battlefield.");
			this.addPattern(inspired());

			CreateTokensFactory bird = new CreateTokensFactory(1, 2, 2, "Put a 2/2 blue Bird enchantment creature token with flying onto the battlefield.");
			bird.setColors(Color.BLUE);
			bird.setSubTypes(SubType.BIRD);
			bird.setEnchantment();
			this.addEffect(ifThen(youMayPay("(2)(U)"), bird.getEventFactory(), "Whenever Aerie Worshippers becomes untapped, you may pay (2)(U). If you do, put a 2/2 blue Bird enchantment creature token with flying onto the battlefield."));
		}
	}

	public AerieWorshippers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Whenever Aerie Worshippers becomes untapped, you may pay (2)(U). If
		// you do, put a 2/2 blue Bird enchantment creature token with flying
		// onto the battlefield.
		this.addAbility(new AerieWorshippersAbility0(state));
	}
}
