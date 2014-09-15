package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("God-Favored General")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class GodFavoredGeneral extends Card
{
	public static final class GodFavoredGeneralAbility0 extends EventTriggeredAbility
	{
		public GodFavoredGeneralAbility0(GameState state)
		{
			super(state, "Whenever God-Favored General becomes untapped, you may pay (2)(W). If you do, put two 1/1 white Soldier enchantment creature tokens onto the battlefield.");
			this.addPattern(inspired());

			EventFactory mayPay = youMayPay("(2)(W)");

			CreateTokensFactory soldiers = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Soldier enchantment creature tokens onto the battlefield.");
			soldiers.setColors(Color.WHITE);
			soldiers.setSubTypes(SubType.SOLDIER);
			soldiers.setEnchantment();
			this.addEffect(ifThen(mayPay, soldiers.getEventFactory(), "You may pay (2)(W). If you do, put two 1/1 white Soldier enchantment creature tokens onto the battlefield."));
		}
	}

	public GodFavoredGeneral(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Inspired \u2014 Whenever God-Favored General becomes untapped, you
		// may pay (2)(W). If you do, put two 1/1 white Soldier enchantment
		// creature tokens onto the battlefield.
		this.addAbility(new GodFavoredGeneralAbility0(state));
	}
}
