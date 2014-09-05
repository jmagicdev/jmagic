package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phyrexian Etchings")
@Types({Type.ENCHANTMENT})
@ManaCost("BBB")
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PhyrexianEtchings extends Card
{
	public static final class DrawTrigger extends EventTriggeredAbility
	{
		public DrawTrigger(GameState state)
		{
			super(state, "At the beginning of your end step, draw a card for each age counter on Phyrexian Etchings.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);
			SetGenerator countAgeCountersOnThis = Count.instance(CountersOn.instance(thisCard, Counter.CounterType.AGE));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndStepOf.instance(controller));
			this.addPattern(pattern);

			EventType.ParameterMap drawParameters = new EventType.ParameterMap();
			drawParameters.put(EventType.Parameter.CAUSE, This.instance());
			drawParameters.put(EventType.Parameter.PLAYER, controller);
			drawParameters.put(EventType.Parameter.NUMBER, countAgeCountersOnThis);
			this.addEffect(new EventFactory(EventType.DRAW_CARDS, drawParameters, "Draw a card for each age counter on Phyrexian Etchings"));
		}
	}

	public static final class LoseLifeTrigger extends EventTriggeredAbility
	{
		public LoseLifeTrigger(GameState state)
		{
			super(state, "When Phyrexian Etchings is put into a graveyard from the battlefield, you lose 2 life for each age counter on it.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator countAgeCountersOnThis = Count.instance(CountersOn.instance(thisCard, Counter.CounterType.AGE));

			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.addEffect(loseLife(You.instance(), Multiply.instance(countAgeCountersOnThis, numberGenerator(2)), "You lose 2 life for each age counter on it."));
		}
	}

	public PhyrexianEtchings(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.CumulativeUpkeep.ForMana(state, "(B)"));
		this.addAbility(new DrawTrigger(state));
		this.addAbility(new LoseLifeTrigger(state));
	}
}
