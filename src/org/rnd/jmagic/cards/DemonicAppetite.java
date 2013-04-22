package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonic Appetite")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DemonicAppetite extends Card
{
	public static final class DemonicPump extends StaticAbility
	{
		public DemonicPump(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +3));
		}
	}

	public static final class BonDemonic extends EventTriggeredAbility
	{
		public BonDemonic(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a creature.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(sacrificeACreature());
		}
	}

	public DemonicAppetite(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// Enchanted creature gets +3/+3.
		this.addAbility(new DemonicPump(state));

		// At the beginning of your upkeep, sacrifice a creature.
		this.addAbility(new BonDemonic(state));
	}
}
