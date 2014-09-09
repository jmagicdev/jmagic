package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Keldon Marauders")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class KeldonMarauders extends Card
{
	public static final class Ping extends EventTriggeredAbility
	{
		public Ping(GameState state)
		{
			super(state, "When Keldon Marauders enters the battlefield or leaves the battlefield, it deals 1 damage to target player.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisLeavesTheBattlefield());

			// it deals 1 damage to target player.
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Keldon Marauders deals 1 damage to target player."));
		}
	}

	public KeldonMarauders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vanishing 2 (This permanent enters the battlefield with two time
		// counters on it. At the beginning of your upkeep, remove a time
		// counter from it. When the last is removed, sacrifice it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vanishing(state, 2));

		this.addAbility(new Ping(state));
	}
}
