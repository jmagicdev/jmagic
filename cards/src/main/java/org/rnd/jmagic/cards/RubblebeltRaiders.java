package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rubblebelt Raiders")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1(R/G)(R/G)(R/G)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class RubblebeltRaiders extends Card
{
	public static final class RubblebeltRaidersAbility0 extends EventTriggeredAbility
	{
		public RubblebeltRaidersAbility0(GameState state)
		{
			super(state, "Whenever Rubblebelt Raiders attacks, put a +1/+1 counter on it for each attacking creature you control.");
			this.addPattern(whenThisAttacks());
			SetGenerator number = Count.instance(Intersect.instance(Attacking.instance(), ControlledBy.instance(You.instance())));
			this.addEffect(putCounters(number, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it for each attacking creature you control."));
		}
	}

	public RubblebeltRaiders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Rubblebelt Raiders attacks, put a +1/+1 counter on it for
		// each attacking creature you control.
		this.addAbility(new RubblebeltRaidersAbility0(state));
	}
}
