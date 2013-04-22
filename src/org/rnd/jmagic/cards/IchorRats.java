package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ichor Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class IchorRats extends Card
{
	public static final class IchorRatsAbility1 extends EventTriggeredAbility
	{
		public IchorRatsAbility1(GameState state)
		{
			super(state, "When Ichor Rats enters the battlefield, each player gets a poison counter.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(putCounters(1, Counter.CounterType.POISON, Players.instance(), "Each player gets a poison counter."));
		}
	}

	public IchorRats(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// When Ichor Rats enters the battlefield, each player gets a poison
		// counter.
		this.addAbility(new IchorRatsAbility1(state));
	}
}
