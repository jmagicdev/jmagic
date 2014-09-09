package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hedron-Field Purists")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class HedronFieldPurists extends Card
{
	private static class Prevention extends DamageReplacementEffect
	{
		private final int n;

		private Prevention(Game game, int n)
		{
			super(game, "If a source would deal damage to you or a creature you control, prevent " + n + " of that damage.");
			this.n = n;
			this.makePreventionEffect();
		}

		// If a source an opponent controls would deal damage to you,
		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);
			Player you = thisObject.getController(thisObject.state);
			Set creaturesYouControl = CREATURES_YOU_CONTROL.evaluate(context.game, thisObject);

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment damage: damageAssignments)
				if(damage.takerID == you.ID || creaturesYouControl.contains(context.state.get(damage.takerID)))
					ret.add(damage);
			return ret;
		}

		// prevent 1 of that damage.
		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			// keys are source IDs, values are how much is prevented
			java.util.Map<Integer, Integer> preventedFrom = new java.util.HashMap<Integer, Integer>();
			java.util.Iterator<DamageAssignment> damageIter = damageAssignments.iterator();
			while(damageIter.hasNext())
			{
				DamageAssignment damage = damageIter.next();
				if(!preventedFrom.containsKey(damage.sourceID))
				{
					damageIter.remove();
					preventedFrom.put(damage.sourceID, 1);
				}
				else
				{
					Integer prevented = preventedFrom.get(damage.sourceID);
					if(prevented < this.n)
					{
						damageIter.remove();
						preventedFrom.put(damage.sourceID, prevented + 1);
					}
				}
			}

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class Prevent1 extends StaticAbility
	{

		public Prevent1(GameState state)
		{
			super(state, "If a source would deal damage to you or a creature you control, prevent 1 of that damage.");
			this.addEffectPart(replacementEffectPart(new Prevention(state.game, 1)));
		}
	}

	public static final class Prevent2 extends StaticAbility
	{
		public Prevent2(GameState state)
		{
			super(state, "If a source would deal damage to you or a creature you control, prevent 2 of that damage.");
			this.addEffectPart(replacementEffectPart(new Prevention(state.game, 2)));
		}
	}

	public HedronFieldPurists(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Level up (2)(W) ((2)(W): Put a level counter on this. Level up only
		// as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(2)(W)"));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 4, 1, 4, "If a source would deal damage to you or a creature you control, prevent 1 of that damage.", Prevent1.class));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 5, 2, 5, "If a source would deal damage to you or a creature you control, prevent 2 of that damage.", Prevent2.class));
	}
}
