package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Swarm of Bloodflies")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class SwarmofBloodflies extends Card
{

	public static final class SwarmofBloodfliesAbility2 extends EventTriggeredAbility
	{
		public SwarmofBloodfliesAbility2(GameState state)
		{
			super(state, "Whenever another creature dies, put a +1/+1 counter on Swarm of Bloodflies.");
			this.addPattern(whenAnotherCreatureDies());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Swarm of Bloodflies."));
		}
	}

	public SwarmofBloodflies(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Swarm of Bloodflies enters the battlefield with two +1/+1 counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 2, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// Whenever another creature dies, put a +1/+1 counter on Swarm of
		// Bloodflies.
		this.addAbility(new SwarmofBloodfliesAbility2(state));
	}
}
