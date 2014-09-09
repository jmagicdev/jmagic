package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stalking Tiger")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class StalkingTiger extends Card
{
	public static final class Stalking extends StaticAbility
	{
		public Stalking(GameState state)
		{
			super(state, "Stalking Tiger can't be blocked by more than one creature.");

			SetGenerator blockingWithMoreThanOneCreature = Intersect.instance(Between.instance(2, null), Count.instance(Blocking.instance(This.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithMoreThanOneCreature));
			this.addEffectPart(part);
		}
	}

	public StalkingTiger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new Stalking(state));
	}
}
