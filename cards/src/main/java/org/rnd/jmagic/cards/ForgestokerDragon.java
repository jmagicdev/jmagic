package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Forgestoker Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class ForgestokerDragon extends Card
{
	public static final class ForgestokerDragonAbility1 extends ActivatedAbility
	{
		public ForgestokerDragonAbility1(GameState state)
		{
			super(state, "(1)(R): Forgestoker Dragon deals 1 damage to target creature. That creature can't block this combat. Activate this ability only if Forgestoker Dragon is attacking.");
			this.setManaCost(new ManaPool("(1)(R)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(1, target, "Forgestoker Dragon deals 1 damage to target creature."));

			EventFactory notePhase = new EventFactory(NOTE, "");
			notePhase.parameters.put(EventType.Parameter.OBJECT, CurrentPhase.instance());
			SetGenerator thisCombat = EffectResult.instance(notePhase);
			SetGenerator sameCombat = Intersect.instance(CurrentPhase.instance(), thisCombat);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), target)));
			this.addEffect(createFloatingEffect(Not.instance(sameCombat), "That creature can't block this combat.", part));

			this.addActivateRestriction(Not.instance(Intersect.instance(ABILITY_SOURCE_OF_THIS, Attacking.instance())));
		}
	}

	public ForgestokerDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R): Forgestoker Dragon deals 1 damage to target creature. That
		// creature can't block this combat. Activate this ability only if
		// Forgestoker Dragon is attacking.
		this.addAbility(new ForgestokerDragonAbility1(state));
	}
}
