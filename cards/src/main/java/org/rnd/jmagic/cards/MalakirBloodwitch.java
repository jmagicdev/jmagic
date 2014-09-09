package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Malakir Bloodwitch")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.VAMPIRE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class MalakirBloodwitch extends Card
{
	public static EventType MALAKIR_BLOODWITCH_EVENT = new EventType("MALAKIR_BLOODWITCH_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		/**
		 * @eparam CAUSE triggered ability from Malakir Bloodwitch
		 * @eparam PLAYER controller of CAUSE
		 * @eparam TARGET opponents of PLAYER
		 * @eparam NUMBER life loss per opponent
		 * @eparam RESULT empty
		 */
		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set trigger = parameters.get(Parameter.CAUSE);
			Set you = parameters.get(Parameter.PLAYER);
			Set opponents = parameters.get(Parameter.TARGET);
			Set number = parameters.get(Parameter.NUMBER);

			// keys are player IDs
			java.util.Map<Integer, Integer> lifeTotals = new java.util.HashMap<Integer, Integer>();
			for(Player p: game.actualState.players)
				lifeTotals.put(p.ID, p.lifeTotal);

			java.util.Map<Parameter, Set> loseLifeParameters = new java.util.HashMap<Parameter, Set>();
			loseLifeParameters.put(Parameter.CAUSE, trigger);
			loseLifeParameters.put(Parameter.PLAYER, opponents);
			loseLifeParameters.put(Parameter.NUMBER, number);
			Event loseLife = createEvent(game, "Each opponent loses life equal to the number of Vampires you control", EventType.LOSE_LIFE, loseLifeParameters);
			loseLife.perform(event, true);

			int lifeLostThisWay = 0;
			for(Player p: game.actualState.players)
			{
				int previousLifeTotal = lifeTotals.get(p.ID);
				if(p.lifeTotal < previousLifeTotal)
					lifeLostThisWay += (previousLifeTotal - p.lifeTotal);
			}

			java.util.Map<Parameter, Set> gainLifeParameters = new java.util.HashMap<Parameter, Set>();
			gainLifeParameters.put(Parameter.CAUSE, trigger);
			gainLifeParameters.put(Parameter.PLAYER, you);
			gainLifeParameters.put(Parameter.NUMBER, new Set(lifeLostThisWay));
			Event gainLife = createEvent(game, "You gain life equal to the life lost this way", EventType.GAIN_LIFE, gainLifeParameters);
			gainLife.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}

	};

	public static final class LifeDrain extends EventTriggeredAbility
	{
		public LifeDrain(GameState state)
		{
			super(state, "When Malakir Bloodwitch enters the battlefield, each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourVampires = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()));

			EventFactory drain = new EventFactory(MALAKIR_BLOODWITCH_EVENT, "Each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way.");
			drain.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drain.parameters.put(EventType.Parameter.PLAYER, You.instance());
			drain.parameters.put(EventType.Parameter.TARGET, OpponentsOf.instance(You.instance()));
			drain.parameters.put(EventType.Parameter.NUMBER, Count.instance(yourVampires));
			this.addEffect(drain);
		}
	}

	public MalakirBloodwitch(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying, protection from white
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromWhite(state));

		// When Malakir Bloodwitch enters the battlefield, each opponent loses
		// life equal to the number of Vampires you control. You gain life equal
		// to the life lost this way.
		this.addAbility(new LifeDrain(state));
	}
}
