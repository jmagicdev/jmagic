package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Pariah")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Pariah extends Card
{
	public static final class NotAVolunteer extends StaticAbility
	{
		public static final class ParialReplacement extends DamageReplacementEffect
		{
			public ParialReplacement(Game game)
			{
				super(game, "All damage that would be dealt to you is dealt to enchanted creature instead.");
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
				GameObject enchantedCreature = this.game.actualState.get(((GameObject)this.getSourceObject(this.game.actualState)).getAttachedTo());

				for(DamageAssignment assignment: damageAssignments.keySet())
					damageAssignments.put(assignment, new DamageAssignment(this.game.actualState.<GameObject>get(assignment.sourceID), enchantedCreature));

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public NotAVolunteer(GameState state)
		{
			super(state, "All damage that would be dealt to you is dealt to enchanted creature instead.");
			this.addEffectPart(replacementEffectPart(new ParialReplacement(state.game)));
		}
	}

	public Pariah(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		this.addAbility(new NotAVolunteer(state));
	}
}
