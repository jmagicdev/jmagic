package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gisela, Blade of Goldnight")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4RWW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class GiselaBladeofGoldnight extends Card
{
	public static final class GiselaBladeofGoldnightAbility1 extends StaticAbility
	{
		public static final class GiselaDoubleDamageEffect extends DamageReplacementEffect
		{
			public GiselaDoubleDamageEffect(Game game)
			{
				super(game, "If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.");
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch batch = new DamageAssignment.Batch();

				for(DamageAssignment assignment: damageAssignments)
				{
					Identified taker = context.state.get(assignment.takerID);
					Set opponents = OpponentsOf.get(context.state, ((Controllable)this.getSourceObject(context.state)).getController(context.state));
					if(taker.isPlayer())
					{
						if(opponents.contains(taker))
							batch.add(assignment);
					}
					else
					{
						if(opponents.contains(((Controllable)taker).getController(context.state)))
							batch.add(assignment);
					}
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

		public GiselaBladeofGoldnightAbility1(GameState state)
		{
			super(state, "If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.");

			this.addEffectPart(replacementEffectPart(new GiselaDoubleDamageEffect(this.game)));
		}
	}

	public static final class GiselaBladeofGoldnightAbility2 extends StaticAbility
	{
		public static final class GiselaPrevent extends DamageReplacementEffect
		{
			private GiselaPrevent(Game game, String name)
			{
				super(game, name);

				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);
				Player you = thisObject.getController(thisObject.state);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment damage: damageAssignments)
				{
					if(damage.takerID == you.ID)
						ret.add(damage);
					else
					{
						Identified taker = context.state.get(damage.takerID);
						if(taker.isGameObject() && ((Controllable)taker).getController(context.state).ID == you.ID)
							ret.add(damage);
					}
				}
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				java.util.Map<Integer, DamageAssignment.Batch> sortedBySource = new java.util.HashMap<>();
				for(DamageAssignment damage: damageAssignments)
				{
					if(!sortedBySource.containsKey(damage.sourceID))
						sortedBySource.put(damage.sourceID, new DamageAssignment.Batch());
					sortedBySource.get(damage.sourceID).add(damage);
				}

				for(java.util.Map.Entry<Integer, DamageAssignment.Batch> sourceEntry: sortedBySource.entrySet())
				{
					java.util.Map<Integer, DamageAssignment.Batch> sortedByTaker = new java.util.HashMap<>();
					for(DamageAssignment damage: sourceEntry.getValue())
					{
						if(!sortedByTaker.containsKey(damage.takerID))
							sortedByTaker.put(damage.takerID, new DamageAssignment.Batch());
						sortedByTaker.get(damage.takerID).add(damage);
					}

					for(java.util.Map.Entry<Integer, DamageAssignment.Batch> takerEntry: sortedByTaker.entrySet())
					{
						int size = takerEntry.getValue().size();
						int remove = size / 2 + size % 2;
						java.util.Iterator<DamageAssignment> iter = takerEntry.getValue().iterator();
						for(int i = 0; i < remove; i++)
							damageAssignments.remove(iter.next());
					}
				}

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public GiselaBladeofGoldnightAbility2(GameState state)
		{
			super(state, "If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.");

			this.addEffectPart(replacementEffectPart(new GiselaPrevent(this.game, "If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.")));
		}
	}

	public GiselaBladeofGoldnight(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// If a source would deal damage to an opponent or a permanent an
		// opponent controls, that source deals double that damage to that
		// player or permanent instead.
		this.addAbility(new GiselaBladeofGoldnightAbility1(state));

		// If a source would deal damage to you or a permanent you control,
		// prevent half that damage, rounded up.
		this.addAbility(new GiselaBladeofGoldnightAbility2(state));
	}
}
