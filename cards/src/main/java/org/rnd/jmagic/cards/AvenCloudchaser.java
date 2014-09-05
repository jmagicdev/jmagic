package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aven Cloudchaser")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AvenCloudchaser extends Card
{
	public static final class AntiEnchantment extends EventTriggeredAbility
	{
		public AntiEnchantment(GameState state)
		{
			super(state, "When Aven Cloudchaser enters the battlefield, destroy target enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(EnchantmentPermanents.instance(), "target enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target enchantment."));
		}
	}

	public AvenCloudchaser(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new AntiEnchantment(state));
	}
}
