package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Primal Cocoon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PrimalCocoon extends Card
{
	public static final class PrimalCocoonAbility1 extends EventTriggeredAbility
	{
		public PrimalCocoonAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Put a +1/+1 counter on enchanted creature."));
		}
	}

	public static final class PrimalCocoonAbility2 extends EventTriggeredAbility
	{
		public PrimalCocoonAbility2(GameState state)
		{
			super(state, "When enchanted creature attacks or blocks, sacrifice Primal Cocoon.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern attacks = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attacks.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(attacks);

			SimpleEventPattern blocks = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			blocks.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(blocks);

			this.addEffect(sacrificeThis("Primal Cocoon"));
		}
	}

	public PrimalCocoon(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// At the beginning of your upkeep, put a +1/+1 counter on enchanted
		// creature.
		this.addAbility(new PrimalCocoonAbility1(state));

		// When enchanted creature attacks or blocks, sacrifice Primal Cocoon.
		this.addAbility(new PrimalCocoonAbility2(state));
	}
}
