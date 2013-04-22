package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodshot Trainee")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BloodshotTrainee extends Card
{
	public static final class BloodshotTraineeAbility0 extends ActivatedAbility
	{
		public BloodshotTraineeAbility0(GameState state)
		{
			super(state, "(T): Bloodshot Trainee deals 4 damage to target creature. Activate this ability only if Bloodshot Trainee's power is 4 or greater.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(4, target, "Bloodshot Trainee deals 4 damage to target creature."));
			this.addActivateRestriction(Intersect.instance(Between.instance(null, 3), PowerOf.instance(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public BloodshotTrainee(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (T): Bloodshot Trainee deals 4 damage to target creature. Activate
		// this ability only if Bloodshot Trainee's power is 4 or greater.
		this.addAbility(new BloodshotTraineeAbility0(state));
	}
}
