package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yavimaya Enchantress")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.HUMAN})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class YavimayaEnchantress extends Card
{
	public static final class EnchantmentPump extends StaticAbility
	{
		public EnchantmentPump(GameState state)
		{
			super(state, "Yavimaya Enchantress gets +1/+1 for each enchantment on the battlefield.");

			SetGenerator numEnchantmentsYouControl = Count.instance(EnchantmentPermanents.instance());

			this.addEffectPart(modifyPowerAndToughness(This.instance(), numEnchantmentsYouControl, numEnchantmentsYouControl));
		}
	}

	public YavimayaEnchantress(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new EnchantmentPump(state));
	}
}
