package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Necropolis Regent")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class NecropolisRegent extends Card
{
	public static final class NecropolisRegentAbility1 extends EventTriggeredAbility
	{
		public NecropolisRegentAbility1(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage to a player, put that many +1/+1 counters on it.");

			this.addPattern(whenDealsCombatDamageToAPlayer(CREATURES_YOU_CONTROL));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatMany = Count.instance(triggerDamage);
			SetGenerator thatCreature = SourceOfDamage.instance(triggerDamage);
			this.addEffect(putCounters(thatMany, Counter.CounterType.PLUS_ONE_PLUS_ONE, thatCreature, "Put that many +1/+1 counters on it."));
		}
	}

	public NecropolisRegent(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a creature you control deals combat damage to a player, put
		// that many +1/+1 counters on it.
		this.addAbility(new NecropolisRegentAbility1(state));
	}
}
