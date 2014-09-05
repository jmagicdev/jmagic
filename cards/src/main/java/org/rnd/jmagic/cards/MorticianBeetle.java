package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mortician Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class MorticianBeetle extends Card
{
	public static final class MorticianBeetleAbility0 extends EventTriggeredAbility
	{
		public MorticianBeetleAbility0(GameState state)
		{
			super(state, "Whenever a player sacrifices a creature, you may put a +1/+1 counter on Mortician Beetle.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.PERMANENT, new ZoneChangeContaining(CreaturePermanents.instance()));
			this.addPattern(pattern);

			EventFactory counterFactory = putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Mortician Beetle");
			this.addEffect(youMay(counterFactory, "You may put a +1/+1 counter on Mortician Beetle."));
		}
	}

	public MorticianBeetle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever a player sacrifices a creature, you may put a +1/+1 counter
		// on Mortician Beetle.
		this.addAbility(new MorticianBeetleAbility0(state));
	}
}
