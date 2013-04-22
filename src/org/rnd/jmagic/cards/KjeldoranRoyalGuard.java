package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Kjeldoran Royal Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class KjeldoranRoyalGuard extends Card
{
	public static final class RoyalShield extends ActivatedAbility
	{
		public static final class RoyalReplacement extends DamageReplacementEffect
		{
			public RoyalReplacement(Game game)
			{
				super(game, "All combat damage that would be dealt to you by unblocked creatures this turn is dealt to Kjeldoran Royal Guard instead.");
				this.makeRedirectionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				for(DamageAssignment assignment: damageAssignments)
				{
					GameObject source = context.state.get(assignment.sourceID);
					if(assignment.isCombatDamage && assignment.takerID == you.ID && source.getAttackingID() != -1 && source.getBlockedByIDs() == null)
						ret.add(assignment);
				}

				return ret;
			}

			@Override
			public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
			{
				NonStaticAbility ability = (NonStaticAbility)this.getSourceObject(this.game.actualState);
				GameObject thisCard = (GameObject)(ability.getSource(this.game.actualState));

				for(DamageAssignment assignment: damageAssignments.keySet())
					damageAssignments.put(assignment, new DamageAssignment(this.game.actualState.<GameObject>get(assignment.sourceID), thisCard));

				return new java.util.LinkedList<EventFactory>();
			}
		}

		public RoyalShield(GameState state)
		{
			super(state, "(T): All combat damage that would be dealt to you by unblocked creatures this turn is dealt to Kjeldoran Royal Guard instead.");
			this.costsTap = true;
			this.addEffect(createFloatingReplacement(new RoyalReplacement(this.game), "All combat damage that would be dealt to you by unblocked creatures this turn is dealt to Kjeldoran Royal Guard instead."));
		}
	}

	public KjeldoranRoyalGuard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		this.addAbility(new RoyalShield(state));
	}
}
