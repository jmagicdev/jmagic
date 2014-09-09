package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Palisade Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.GIANT})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class PalisadeGiant extends Card
{
	public static final class PalisadeGiantAbility0 extends StaticAbility
	{
		public static final class EmpyrialReplacement extends DamageReplacementEffect
		{
			public EmpyrialReplacement(Game game)
			{
				super(game, "All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead");
				this.makeRedirectionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == you.ID)
						ret.add(assignment);
					else
					{
						Identified taker = context.game.actualState.get(assignment.takerID);
						if(taker instanceof Controllable && ((Controllable)taker).getController(context.game.actualState).ID == you.ID)
							ret.add(assignment);
					}

				return ret;
			}

			@Override
			public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
			{
				GameObject thisCard = (GameObject)this.getSourceObject(this.game.actualState);

				// TODO : consider caching the source of the damage instead of
				// fetching it every loop. this also applies to Empyrial
				// Archangel's effect
				for(DamageAssignment assignment: damageAssignments.keySet())
					damageAssignments.put(assignment, new DamageAssignment(this.game.actualState.<GameObject>get(assignment.sourceID), thisCard));

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public PalisadeGiantAbility0(GameState state)
		{
			super(state, "All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead.");

			this.addEffectPart(replacementEffectPart(new EmpyrialReplacement(this.game)));
		}
	}

	public PalisadeGiant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(7);

		// All damage that would be dealt to you or another permanent you
		// control is dealt to Palisade Giant instead.
		this.addAbility(new PalisadeGiantAbility0(state));
	}
}
