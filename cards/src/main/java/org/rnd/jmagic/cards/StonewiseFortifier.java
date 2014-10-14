package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stonewise Fortifier")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class StonewiseFortifier extends Card
{
	private static final class Prevent extends DamageReplacementEffect
	{
		private SetGenerator preventTo;
		private SetGenerator preventFrom;

		private Prevent(Game game, String name, SetGenerator preventTo, SetGenerator preventFrom)
		{
			super(game, name);
			this.preventTo = preventTo;
			this.preventFrom = preventFrom;
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			Identified ability = this.getSourceObject(context.game.actualState);

			java.util.Set<Integer> takerIDs = new java.util.HashSet<Integer>();
			for(Identified identified: this.preventTo.evaluate(context.state, ability).getAll(Identified.class))
				takerIDs.add(identified.ID);

			java.util.Set<Integer> sourceIDs = new java.util.HashSet<Integer>();
			for(Identified identified: this.preventFrom.evaluate(context.state, ability).getAll(Identified.class))
				sourceIDs.add(identified.ID);

			for(DamageAssignment assignment: damageAssignments)
			{
				if(!takerIDs.contains(assignment.takerID))
					continue;
				if(sourceIDs.contains(assignment.sourceID))
					ret.add(assignment);
			}

			return ret;
		}

		@Override
		public java.util.List<org.rnd.jmagic.engine.EventFactory> prevent(org.rnd.jmagic.engine.DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return java.util.Collections.emptyList();
		}
	}

	public static final class StonewiseFortifierAbility0 extends ActivatedAbility
	{
		public StonewiseFortifierAbility0(GameState state)
		{
			super(state, "(4)(W): Prevent all damage that would be dealt to Stonewise Fortifier by target creature this turn.");
			this.setManaCost(new ManaPool("(4)(W)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			DamageReplacementEffect prevent = new Prevent(state.game, "Prevent all damage that would be dealt to Stonewise Fortifier by target creature", ABILITY_SOURCE_OF_THIS, target);
			this.addEffect(createFloatingReplacement(prevent, "Prevent all damage that would be dealt to Stonewise Fortifier by target creature this turn."));
		}
	}

	public StonewiseFortifier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (4)(W): Prevent all damage that would be dealt to Stonewise Fortifier
		// by target creature this turn.
		this.addAbility(new StonewiseFortifierAbility0(state));
	}
}
