package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class CumulativeUpkeep extends Keyword
{
	public CumulativeUpkeep(GameState state, String costName)
	{
		super(state, "Cumulative upkeep - " + costName);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new CumulativeUpkeepAbility(this.state, this));

		return ret;
	}

	/**
	 * Child classes will use these to create the event for their cost on demand
	 */
	protected abstract EventFactory getFactory(SetGenerator thisAbility);

	public static final class CumulativeUpkeepAbility extends EventTriggeredAbility
	{
		private final CumulativeUpkeep parent;

		public CumulativeUpkeepAbility(GameState state, CumulativeUpkeep parent)
		{
			super(state, "At the beginning of your upkeep, if this permanent is on the battlefield, put an age counter on this permanent. Then you may pay its cumulative upkeep cost for each age counter on it. If you don't, sacrifice it.");
			this.parent = parent;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory cost = parent.getFactory(This.instance());

			this.addPattern(atTheBeginningOfYourUpkeep());
			this.interveningIf = Intersect.instance(ABILITY_SOURCE_OF_THIS, Permanents.instance());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.AGE, "this permanent"));

			EventFactory payFactory = new EventFactory(EventType.PAY_CUMULATIVE_UPKEEP, (cost.name + " for each age counter on it."));
			payFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(cost));
			payFactory.parameters.put(EventType.Parameter.NUMBER, Count.instance(CountersOn.instance(thisCard, Counter.CounterType.AGE)));

			EventFactory mayFactory = youMay(payFactory, "You may " + cost.name + " for each age counter on it.");
			EventFactory sacFactory = sacrificeThis("it");

			EventFactory mainFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, ("You may " + cost.name + " for each age counter on it.  If you don't, sacrifice it."));
			mainFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayFactory));
			mainFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(sacFactory));
			this.addEffect(mainFactory);
		}

		@Override
		public CumulativeUpkeepAbility create(Game game)
		{
			return new CumulativeUpkeepAbility(game.physicalState, this.parent);
		}
	}

	public static final class ForMana extends CumulativeUpkeep
	{
		private final String cumulativeUpkeep;

		public ForMana(GameState state, String manaCost)
		{
			super(state, "Pay " + manaCost);

			this.cumulativeUpkeep = manaCost;
		}

		@Override
		public ForMana create(Game game)
		{
			return new ForMana(game.physicalState, this.cumulativeUpkeep);
		}

		@Override
		protected EventFactory getFactory(SetGenerator thisAbility)
		{
			EventFactory factory = new EventFactory(EventType.PAY_MANA, "Pay " + this.cumulativeUpkeep);
			factory.parameters.put(EventType.Parameter.CAUSE, thisAbility);
			factory.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool(this.cumulativeUpkeep)));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(thisAbility));
			return factory;
		}
	}
}
