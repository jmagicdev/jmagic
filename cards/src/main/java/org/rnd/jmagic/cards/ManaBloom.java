package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Bloom")
@Types({Type.ENCHANTMENT})
@ManaCost("XG")
@ColorIdentity({Color.GREEN})
public final class ManaBloom extends Card
{
	public static final class ManaBloomAbility1 extends ActivatedAbility
	{
		public ManaBloomAbility1(GameState state)
		{
			super(state, "Remove a charge counter from Mana Bloom: Add one mana of any color to your mana pool. Activate this ability only once each turn.");

			// Remove a charge counter from Mana Bloom
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Mana Bloom"));

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)", "Add one mana of any color to your mana pool."));

			this.perTurnLimit(1);
		}
	}

	public static final class ManaBloomAbility2 extends EventTriggeredAbility
	{
		public ManaBloomAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Not.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return it to its owner's hand."));
		}
	}

	public ManaBloom(GameState state)
	{
		super(state);

		// Mana Bloom enters the battlefield with X charge counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X charge counters on it", Counter.CounterType.CHARGE));

		// Remove a charge counter from Mana Bloom: Add one mana of any color to
		// your mana pool. Activate this ability only once each turn.
		this.addAbility(new ManaBloomAbility1(state));

		// At the beginning of your upkeep, if Mana Bloom has no charge counters
		// on it, return it to its owner's hand.
		this.addAbility(new ManaBloomAbility2(state));
	}
}
