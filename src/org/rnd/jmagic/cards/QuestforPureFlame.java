package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quest for Pure Flame")
@Types({Type.ENCHANTMENT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class QuestforPureFlame extends Card
{
	public static final class FireIsGood extends EventTriggeredAbility
	{
		private static final class SourcesYouControl extends SetGenerator
		{
			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set sources = AllSourcesOfDamage.instance().evaluate(state, thisObject);
				NonStaticAbility questCounterTrigger = (NonStaticAbility)thisObject;
				GameObject questForPureFlame = (GameObject)(questCounterTrigger.getSource(state));
				int controller = questForPureFlame.controllerID;

				Set ret = new Set();
				for(GameObject source: sources.getAll(GameObject.class))
					if(source.controllerID == controller)
						ret.add(source);

				return ret;
			}
		}

		public FireIsGood(GameState state)
		{
			super(state, "Whenever a source you control deals damage to an opponent, you may put a quest counter on Quest for Pure Flame.");

			SetGenerator sourcesYouControl = new SourcesYouControl();
			this.addPattern(whenDealsDamageToAnOpponent(sourcesYouControl));

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for Pure Flame"));
		}
	}

	public static final class DoubleDamageEffect extends DamageReplacementEffect
	{
		public DoubleDamageEffect(Game game)
		{
			super(game, "If any source you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.");
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Player you = ((Controllable)this.getSourceObject(this.game.actualState)).getController(this.game.actualState);

			DamageAssignment.Batch batch = new DamageAssignment.Batch();

			for(DamageAssignment assignment: damageAssignments)
			{
				Identified taker = context.state.get(assignment.takerID);
				boolean dealingToPlayer = taker.isPlayer();
				boolean dealingToCreature = (taker.isGameObject()) && (((GameObject)taker).getTypes().contains(Type.CREATURE));
				if(!dealingToPlayer && !dealingToCreature)
					continue;

				if(context.state.<GameObject>get(assignment.sourceID).getController(this.game.actualState).equals(you))
					batch.add(assignment);
			}

			return batch;
		}

		@Override
		public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
		{
			java.util.Collection<DamageAssignment> duplicates = new java.util.LinkedList<DamageAssignment>();
			for(DamageAssignment assignment: damageAssignments)
				duplicates.add(new DamageAssignment(assignment));
			damageAssignments.addAll(duplicates);

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class FireIsAwesome extends ActivatedAbility
	{
		public FireIsAwesome(GameState state)
		{
			super(state, "Remove four quest counters from Quest for Pure Flame and sacrifice it: If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.");
			this.addCost(removeCountersFromThis(4, Counter.CounterType.QUEST, "Quest for Pure Flame"));

			this.addCost(sacrificeThis("Quest for Pure Flame"));

			this.addEffect(createFloatingReplacement(new DoubleDamageEffect(state.game), "If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead."));
		}
	}

	public QuestforPureFlame(GameState state)
	{
		super(state);

		// Whenever a source you control deals damage to an opponent, you may
		// put a quest counter on Quest for Pure Flame.
		this.addAbility(new FireIsGood(state));

		// Remove four quest counters from Quest for Pure Flame and sacrifice
		// it: If any source you control would deal damage to a creature or
		// player this turn, it deals double that damage to that creature or
		// player instead.
		this.addAbility(new FireIsAwesome(state));
	}
}
