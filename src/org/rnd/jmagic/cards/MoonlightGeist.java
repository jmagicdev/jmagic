package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Moonlight Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MoonlightGeist extends Card
{
	public static final class MoonlightGeistAbility1 extends ActivatedAbility
	{
		public static final class PreventDamageEffect extends DamageReplacementEffect
		{
			public PreventDamageEffect(Game game, String name)
			{
				super(game, name);
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				ActivatedAbility source = (ActivatedAbility)this.getSourceObject(context.game.actualState);
				int abilitySource = source.getSourceID();

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
				{
					if(!assignment.isCombatDamage)
						continue;
					if(assignment.sourceID == abilitySource || assignment.takerID == abilitySource)
						ret.add(assignment);
				}
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}
		}

		public MoonlightGeistAbility1(GameState state)
		{
			super(state, "(3)(W): Prevent all combat damage that would be dealt to and dealt by Moonlight Geist this turn.");
			this.setManaCost(new ManaPool("(3)(W)"));

			ReplacementEffect prevent = new PreventDamageEffect(state.game, "");
			this.addEffect(createFloatingReplacement(prevent, "Prevent all combat damage that would be dealt to and dealt by Moonlight Geist this turn."));
		}
	}

	public MoonlightGeist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (3)(W): Prevent all combat damage that would be dealt to and dealt by
		// Moonlight Geist this turn.
		this.addAbility(new MoonlightGeistAbility1(state));
	}
}
