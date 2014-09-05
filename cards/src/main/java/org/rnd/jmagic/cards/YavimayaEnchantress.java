package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Yavimaya Enchantress")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.HUMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.UNCOMMON)})
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
