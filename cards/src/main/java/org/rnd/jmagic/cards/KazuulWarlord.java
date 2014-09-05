package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kazuul Warlord")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.ALLY, SubType.WARRIOR})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class KazuulWarlord extends Card
{
	public static final class WarlordAlly extends EventTriggeredAbility
	{
		public WarlordAlly(GameState state)
		{
			super(state, "Whenever Kazuul Warlord or another Ally enters the battlefield under your control, you may put a +1/+1 counter on each Ally creature you control.");
			this.addPattern(allyTrigger());
			EventFactory counterFactory = putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ALLY_CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each Ally creature you control");
			this.addEffect(youMay(counterFactory, "You may put a +1/+1 counter on each Ally creature you control."));
		}
	}

	public KazuulWarlord(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new WarlordAlly(state));
	}
}
