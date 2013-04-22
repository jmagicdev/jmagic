package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guardian Seraph")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class GuardianSeraph extends Card
{
	public static final class PreventOneDamage extends StaticAbility
	{
		private static class PreventOneReplacement extends DamageReplacementEffect
		{
			private PreventOneReplacement(Game game)
			{
				super(game, "If a source an opponent controls would deal damage to you, prevent 1 of that damage.");
				this.makePreventionEffect();
			}

			// If a source an opponent controls would deal damage to you,
			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);
				Player you = thisObject.getController(thisObject.state);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment damage: damageAssignments)
				{
					if(damage.takerID != you.ID)
						continue;

					Controllable source = (Controllable)(context.state.get(damage.sourceID));
					Player controller = source.getController(context.state);
					if(!OpponentsOf.get(context.state, you).contains(controller))
						continue;

					ret.add(damage);
				}
				return ret;
			}

			// prevent 1 of that damage.
			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				java.util.Set<Integer> preventedFrom = new java.util.HashSet<Integer>();
				java.util.Iterator<DamageAssignment> damageIter = damageAssignments.iterator();
				while(damageIter.hasNext())
				{
					DamageAssignment damage = damageIter.next();
					if(!preventedFrom.contains(damage.sourceID))
					{
						damageIter.remove();
						preventedFrom.add(damage.sourceID);
					}
				}

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public PreventOneDamage(GameState state)
		{
			super(state, "If a source an opponent controls would deal damage to you, prevent 1 of that damage.");

			this.addEffectPart(replacementEffectPart(new PreventOneReplacement(state.game)));
		}
	}

	public GuardianSeraph(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new PreventOneDamage(state));
	}
}
