package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Training Drone")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.DRONE})
@ManaCost("3")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TrainingDrone extends Card
{
	public static final class TrainingDroneAbility0 extends StaticAbility
	{
		public TrainingDroneAbility0(GameState state)
		{
			super(state, "Training Drone can't attack or block unless it's equipped.");

			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Attacking.instance())));
			this.addEffectPart(restrictions);

			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Blocking.instance())));
			this.addEffectPart(block);

			this.canApply = Both.instance(this.canApply, Not.instance(Intersect.instance(EquippedBy.instance(HasSubType.instance(SubType.EQUIPMENT)), This.instance())));
		}
	}

	public TrainingDrone(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Training Drone can't attack or block unless it's equipped.
		this.addAbility(new TrainingDroneAbility0(state));
	}
}
