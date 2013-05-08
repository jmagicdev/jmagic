package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cliffside Market")
@Types({Type.PLANE})
@SubTypes({SubType.MERCADIA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class CliffsideMarket extends Card
{
	public static final class PermanentsThatShareAType extends SetGenerator
	{
		private static final PermanentsThatShareAType _instance = new PermanentsThatShareAType();

		public static PermanentsThatShareAType instance()
		{
			return _instance;
		}

		private PermanentsThatShareAType()
		{
			// Singleton Constructor
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();

			java.util.List<GameObject> targets = new java.util.LinkedList<GameObject>(Permanents.instance().evaluate(state, thisObject).getAll(GameObject.class));

			java.util.ListIterator<GameObject> firstIter = targets.listIterator();
			int size = targets.size();

			firstLoop: while(firstIter.hasNext())
			{
				GameObject firstObj = firstIter.next();
				int nextIndex = firstIter.nextIndex();
				if(nextIndex == size)
					return ret;
				java.util.ListIterator<GameObject> secIter = targets.listIterator(nextIndex);
				while(secIter.hasNext())
				{
					GameObject secObj = secIter.next();
					for(Type type: secObj.getTypes())
						if(firstObj.getTypes().contains(type))
						{
							ret.add(firstObj);
							ret.add(secObj);
							continue firstLoop;
						}
				}
			}

			return ret;
		}
	}

	public static final class ConfusionInTheLifeTotals extends EventTriggeredAbility
	{
		public ConfusionInTheLifeTotals(GameState state)
		{
			super(state, "When you planeswalk to Cliffside Market or at the beginning of your upkeep, you may exchange life totals with target player.");

			SimpleEventPattern pattern = new SimpleEventPattern(Planechase.PLANESWALK);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.TO, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);
			this.addPattern(atTheBeginningOfYourUpkeep());

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory factory = new EventFactory(EventType.EXCHANGE_LIFE_TOTALS, "Exchange life totals with target player.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, Union.instance(targetedBy(target), You.instance()));
			this.addEffect(youMay(factory, "You may exchange life totals with target player."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosLegerdemain extends EventTriggeredAbility
	{
		public ChaosLegerdemain(GameState state)
		{
			super(state, "Whenever you roll (C), exchange control of two target permanents that share a card type.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target1 = this.addTarget(PermanentsThatShareAType.instance(), "target permanent");

			SetGenerator firstTarget = targetedBy(target1);
			Target target2 = this.addTarget(IfThenElse.instance(firstTarget, Intersect.instance(Permanents.instance(), HasType.instance(CardTypeOf.instance(firstTarget))), PermanentsThatShareAType.instance()), "another target permanent that shares a type with the first target");

			EventFactory factory = new EventFactory(EventType.EXCHANGE_CONTROL, "Exchange control of two target permanents that share a card type.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target1, target2));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public CliffsideMarket(GameState state)
	{
		super(state);

		this.addAbility(new ConfusionInTheLifeTotals(state));

		this.addAbility(new ChaosLegerdemain(state));
	}
}
