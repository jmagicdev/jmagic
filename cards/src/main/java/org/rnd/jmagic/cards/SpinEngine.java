package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spin Engine")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@ColorIdentity({Color.RED})
public final class SpinEngine extends Card
{
	public static final class SpinEngineAbility0 extends ActivatedAbility
	{
		public SpinEngineAbility0(GameState state)
		{
			super(state, "(R): Target creature can't block Spin Engine this turn.");
			this.setManaCost(new ManaPool("(R)"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator restriction = Intersect.instance(Blocking.instance(ABILITY_SOURCE_OF_THIS), targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));

			this.addEffect(createFloatingEffect("Target creature can't block Spin Engine this turn", part));
		}
	}

	public SpinEngine(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// (R): Target creature can't block Spin Engine this turn.
		this.addAbility(new SpinEngineAbility0(state));
	}
}
