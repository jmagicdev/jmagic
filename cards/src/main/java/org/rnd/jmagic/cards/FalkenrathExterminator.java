package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Falkenrath Exterminator")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHER, SubType.VAMPIRE})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class FalkenrathExterminator extends Card
{
	public static final class FalkenrathExterminatorAbility0 extends EventTriggeredAbility
	{
		public FalkenrathExterminatorAbility0(GameState state)
		{
			super(state, "Whenever Falkenrath Exterminator deals combat damage to a player, put a +1/+1 counter on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public static final class FalkenrathExterminatorAbility1 extends ActivatedAbility
	{
		public FalkenrathExterminatorAbility1(GameState state)
		{
			super(state, "(2)(R): Falkenrath Exterminator deals damage to target creature equal to the number of +1/+1 counters on Falkenrath Exterminator.");
			this.setManaCost(new ManaPool("(2)(R)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE)), target, "Falkenrath Exterminator deals damage to target creature equal to the number of +1/+1 counters on Falkenrath Exterminator."));
		}
	}

	public FalkenrathExterminator(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Falkenrath Exterminator deals combat damage to a player, put
		// a +1/+1 counter on it.
		this.addAbility(new FalkenrathExterminatorAbility0(state));

		// (2)(R): Falkenrath Exterminator deals damage to target creature equal
		// to the number of +1/+1 counters on Falkenrath Exterminator.
		this.addAbility(new FalkenrathExterminatorAbility1(state));
	}
}
