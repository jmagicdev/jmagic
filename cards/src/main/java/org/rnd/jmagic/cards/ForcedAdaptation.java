package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Forced Adaptation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ForcedAdaptation extends Card
{
	public static final class ForcedAdaptationAbility1 extends EventTriggeredAbility
	{
		public ForcedAdaptationAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Put a +1/+1 counter on enchanted creature."));
		}
	}

	public ForcedAdaptation(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// At the beginning of your upkeep, put a +1/+1 counter on enchanted
		// creature.
		this.addAbility(new ForcedAdaptationAbility1(state));
	}
}
