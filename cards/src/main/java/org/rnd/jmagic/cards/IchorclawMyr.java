package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ichorclaw Myr")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("2")
@ColorIdentity({})
public final class IchorclawMyr extends Card
{
	public static final class IchorclawMyrAbility1 extends EventTriggeredAbility
	{
		public IchorclawMyrAbility1(GameState state)
		{
			super(state, "Whenever Ichorclaw Myr becomes blocked, it gets +2/+2 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "It gets +2/+2 until end of turn."));
		}
	}

	public IchorclawMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Whenever Ichorclaw Myr becomes blocked, it gets +2/+2 until end of
		// turn.
		this.addAbility(new IchorclawMyrAbility1(state));
	}
}
