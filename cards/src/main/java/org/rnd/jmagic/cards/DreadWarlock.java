package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dread Warlock")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class DreadWarlock extends Card
{
	public static final class BlockingRestriction extends StaticAbility
	{
		public BlockingRestriction(GameState state)
		{
			super(state, "Dread Warlock can't be blocked except by black creatures.");

			SetGenerator blockingThis = Blocking.instance(This.instance());
			SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
			SetGenerator restriction = Intersect.instance(blockingThis, nonblackCreatures);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));

			this.addEffectPart(part);
		}
	}

	public DreadWarlock(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new BlockingRestriction(state));
	}
}
