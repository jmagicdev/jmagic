package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Olivia Voldaren")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class OliviaVoldaren extends Card
{
	public static final class OliviaVoldarenAbility1 extends ActivatedAbility
	{
		public OliviaVoldarenAbility1(GameState state)
		{
			super(state, "(1)(R): Olivia Voldaren deals 1 damage to another target creature. That creature becomes a Vampire in addition to its other types. Put a +1/+1 counter on Olivia Voldaren.");
			this.setManaCost(new ManaPool("(1)(R)"));

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "another target creature"));
			this.addEffect(permanentDealDamage(1, target, "Olivia Voldaren deals 1 damage to another target creature."));

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.VAMPIRE));
			this.addEffect(createFloatingEffect(Empty.instance(), "That creature becomes a Vampire in addition to its other types.", typesPart));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Olivia Voldaren."));
		}
	}

	public static final class OliviaVoldarenAbility2 extends ActivatedAbility
	{
		public OliviaVoldarenAbility2(GameState state)
		{
			super(state, "(3)(B)(B): Gain control of target Vampire for as long as you control Olivia Voldaren.");
			this.setManaCost(new ManaPool("(3)(B)(B)"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.VAMPIRE)), "target Vampire"));
			SetGenerator youControlThis = Intersect.instance(ABILITY_SOURCE_OF_THIS, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect(Not.instance(youControlThis), "Gain control of target Vampire for as long as you control Olivia Voldaren.", part));
		}
	}

	public OliviaVoldaren(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R): Olivia Voldaren deals 1 damage to another target creature.
		// That creature becomes a Vampire in addition to its other types. Put a
		// +1/+1 counter on Olivia Voldaren.
		this.addAbility(new OliviaVoldarenAbility1(state));

		// (3)(B)(B): Gain control of target Vampire for as long as you control
		// Olivia Voldaren.
		this.addAbility(new OliviaVoldarenAbility2(state));
	}
}
