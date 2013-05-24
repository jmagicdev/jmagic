package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Empyrial Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4GWWU")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class EmpyrialArchangel extends Card
{
	public static final class EmpyrialShield extends StaticAbility
	{
		public static final class EmpyrialReplacement extends DamageReplacementEffect
		{
			public EmpyrialReplacement(Game game)
			{
				super(game, "All damage that would be dealt to you is dealt to Empyrial Archangel instead");
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

				return ret;
			}

			@Override
			public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
			{
				GameObject thisCard = (GameObject)this.getSourceObject(this.game.actualState);

				for(DamageAssignment assignment: damageAssignments.keySet())
					damageAssignments.put(assignment, new DamageAssignment(this.game.actualState.<GameObject>get(assignment.sourceID), thisCard));

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public EmpyrialShield(GameState state)
		{
			super(state, "All damage that would be dealt to you is dealt to Empyrial Archangel instead.");

			this.addEffectPart(replacementEffectPart(new EmpyrialReplacement(this.game)));
		}
	}

	public EmpyrialArchangel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new EmpyrialShield(state));
	}
}
