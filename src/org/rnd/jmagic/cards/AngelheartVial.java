package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelheart Vial")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({})
public final class AngelheartVial extends Card
{
	public static final class AlkalinePain extends EventTriggeredAbility
	{
		public AlkalinePain(GameState state)
		{
			super(state, "Whenever you're dealt damage, you may put that many charge counters on Angelheart Vial.");

			this.addPattern(whenIsDealtDamage(You.instance()));

			this.addEffect(youMay(putCounters(Count.instance(TriggerDamage.instance(This.instance())), Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put that many charge counters on Angelheart Vial."), "You may put that many charge counters on Angelheart Vial."));
		}
	}

	public static final class AngelheartVialAbility1 extends ActivatedAbility
	{
		public AngelheartVialAbility1(GameState state)
		{
			super(state, "(2), (T), Remove four charge counters from Angelheart Vial: You gain 2 life and draw a card.");

			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCountersFromThis(4, Counter.CounterType.CHARGE, "Angelheart Vial"));

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life"));

			this.addEffect(drawCards(You.instance(), 1, "and draw a card."));
		}
	}

	public AngelheartVial(GameState state)
	{
		super(state);

		// Whenever you're dealt damage, you may put that many charge counters
		// on Angelheart Vial.
		this.addAbility(new AlkalinePain(state));

		// (2), (T), Remove four charge counters from Angelheart Vial: You gain
		// 2 life and draw a card.
		this.addAbility(new AngelheartVialAbility1(state));
	}
}
