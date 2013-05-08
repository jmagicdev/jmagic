package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("War Priest of Thune")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class WarPriestofThune extends Card
{
	public static final class WarPriestofThuneAbility0 extends EventTriggeredAbility
	{
		public WarPriestofThuneAbility0(GameState state)
		{
			super(state, "When War Priest of Thune enters the battlefield, you may destroy target enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(youMay(destroy(target, "Destroy target enchantment"), "You may destroy target enchantment."));
		}
	}

	public WarPriestofThune(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When War Priest of Thune enters the battlefield, you may destroy
		// target enchantment.
		this.addAbility(new WarPriestofThuneAbility0(state));
	}
}
