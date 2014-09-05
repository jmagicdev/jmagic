package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rakish Heir")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RakishHeir extends Card
{
	public static final class RakishHeirAbility0 extends EventTriggeredAbility
	{
		public RakishHeirAbility0(GameState state)
		{
			super(state, "Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.");
			this.addPattern(whenDealsCombatDamageToAPlayer(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.VAMPIRE))));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, SourceOfDamage.instance(TriggerDamage.instance(This.instance())), "Put a +1/+1 counter on it."));
		}
	}

	public RakishHeir(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever a Vampire you control deals combat damage to a player, put a
		// +1/+1 counter on it.
		this.addAbility(new RakishHeirAbility0(state));
	}
}
