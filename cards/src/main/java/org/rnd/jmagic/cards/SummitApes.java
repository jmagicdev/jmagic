package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Summit Apes")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class SummitApes extends Card
{
	public static final class SummitApesAbility0 extends StaticAbility
	{
		public SummitApesAbility0(GameState state)
		{
			super(state, "As long as you control a Mountain, Summit Apes can't be blocked except by two or more creatures.");

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator mountains = HasSubType.instance(SubType.MOUNTAIN);
			this.canApply = Both.instance(Intersect.instance(youControl, mountains), this.canApply);

			SetGenerator blockingWithOneCreature = Intersect.instance(numberGenerator(1), Count.instance(Blocking.instance(This.instance())));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithOneCreature));
			this.addEffectPart(part);
		}
	}

	public SummitApes(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);

		// As long as you control a Mountain, Summit Apes can't be blocked
		// except by two or more creatures.
		this.addAbility(new SummitApesAbility0(state));
	}
}
