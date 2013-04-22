package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Druids' Repository")
@Types({Type.ENCHANTMENT})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DruidsRepository extends Card
{
	public static final class DruidsRepositoryAbility0 extends EventTriggeredAbility
	{
		public DruidsRepositoryAbility0(GameState state)
		{
			super(state, "Whenever a creature you control attacks, put a charge counter on Druids' Repository.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Druids' Repository."));
		}
	}

	public static final class DruidsRepositoryAbility1 extends ActivatedAbility
	{
		public DruidsRepositoryAbility1(GameState state)
		{
			super(state, "Remove a charge counter from Druids' Repository: Add one mana of any color to your mana pool.");
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Druids' Repository"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)", "Add one mana of any color to your mana pool."));
		}
	}

	public DruidsRepository(GameState state)
	{
		super(state);

		// Whenever a creature you control attacks, put a charge counter on
		// Druids' Repository.
		this.addAbility(new DruidsRepositoryAbility0(state));

		// Remove a charge counter from Druids' Repository: Add one mana of any
		// color to your mana pool.
		this.addAbility(new DruidsRepositoryAbility1(state));
	}
}
