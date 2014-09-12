package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avacyn, Guardian Angel")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WWW")
@ColorIdentity({Color.WHITE})
public final class AvacynGuardianAngel extends Card
{
	private static final class Prevent extends DamageReplacementEffect
	{
		private SetGenerator target;
		private SetGenerator color;

		private Prevent(Game game, String name, SetGenerator target, SetGenerator color)
		{
			super(game, name);
			this.target = target;
			this.color = color;
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			Identified ability = this.getSourceObject(context.game.actualState);

			java.util.Set<Integer> takerIDs = new java.util.HashSet<Integer>();
			for(Identified identified: this.target.evaluate(context.state, ability).getAll(Identified.class))
				takerIDs.add(identified.ID);

			Color colorChoice = this.color.evaluate(context.game, ability).getOne(Color.class);
			for(DamageAssignment assignment: damageAssignments)
			{
				if(!takerIDs.contains(assignment.takerID))
					continue;
				if(context.game.actualState.<GameObject>get(assignment.sourceID).getColors().contains(colorChoice))
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

	public static final class AvacynGuardianAngelAbility1 extends ActivatedAbility
	{
		public AvacynGuardianAngelAbility1(GameState state)
		{
			super(state, "(1)(W): Prevent all damage that would be dealt to another target creature this turn by sources of the color of your choice.");
			this.setManaCost(new ManaPool("(1)(W)"));

			SetGenerator anotherTargetCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherTargetCreature, "another target creature"));

			SetGenerator colors = Identity.fromCollection(Color.allColors());
			EventFactory chooseColor = playerChoose(You.instance(), 1, colors, PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "Choose a color.");
			this.addEffect(chooseColor);
			Prevent effect = new Prevent(this.game, "Prevent all damage that would be dealt to another target creature this turn by sources of the color of your choice.", target, EffectResult.instance(chooseColor));
			this.addEffect(createFloatingEffect("Prevent all damage that would be dealt to another target creature this turn by sources of the color of your choice.", replacementEffectPart(effect)));
		}
	}

	public static final class AvacynGuardianAngelAbility2 extends ActivatedAbility
	{
		public AvacynGuardianAngelAbility2(GameState state)
		{
			super(state, "(5)(W)(W): Prevent all damage that would be dealt to target player this turn by sources of the color of your choice.");
			this.setManaCost(new ManaPool("(5)(W)(W)"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator colors = Identity.fromCollection(Color.allColors());
			EventFactory chooseColor = playerChoose(You.instance(), 1, colors, PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "Choose a color.");
			this.addEffect(chooseColor);
			Prevent effect = new Prevent(this.game, "Prevent all damage that would be dealt to target player this turn by sources of the color of your choice.", target, EffectResult.instance(chooseColor));
			this.addEffect(createFloatingEffect("Prevent all damage that would be dealt to target player this turn by sources of the color of your choice.", replacementEffectPart(effect)));
		}
	}

	public AvacynGuardianAngel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (1)(W): Prevent all damage that would be dealt to another target
		// creature this turn by sources of the color of your choice.
		this.addAbility(new AvacynGuardianAngelAbility1(state));

		// (5)(W)(W): Prevent all damage that would be dealt to target player
		// this turn by sources of the color of your choice.
		this.addAbility(new AvacynGuardianAngelAbility2(state));
	}
}
