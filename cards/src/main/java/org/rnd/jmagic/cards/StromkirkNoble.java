package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stromkirk Noble")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class StromkirkNoble extends Card
{
	public static final class Humanwalk extends StaticAbility
	{
		public Humanwalk(GameState state)
		{
			super(state, "Stromkirk Noble can't be blocked by Humans.");

			SetGenerator humanBlocking = Intersect.instance(HasSubType.instance(SubType.HUMAN), Blocking.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(humanBlocking));
			this.addEffectPart(part);
		}
	}

	public StromkirkNoble(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Humanwalk(state));
		this.addAbility(new org.rnd.jmagic.abilities.MeleeGetPlusOnePlusOneCounters(state, this.getName(), 1));
	}
}
