package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spirit Link")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Legends.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SpiritLink extends Card
{
	public static final class LifeGain extends EventTriggeredAbility
	{
		public LifeGain(GameState state)
		{
			super(state, "Whenever enchanted creature deals damage, you gain that much life.");

			this.addPattern(whenDealsDamage(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator totalDamageDealt = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(gainLife(You.instance(), totalDamageDealt, "You gain that much life."));
		}
	}

	public SpiritLink(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new LifeGain(state));
	}
}
