package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Belligerent Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class BelligerentSliver extends Card
{
	public static final class Belligerent extends StaticAbility
	{
		public Belligerent(GameState state)
		{
			super(state, "This creature can't be blocked except by two or more creatures.");

			SetGenerator restriction = Intersect.instance(Count.instance(Blocking.instance(This.instance())), numberGenerator(1));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public static final class BelligerentSliverAbility0 extends StaticAbility
	{
		public BelligerentSliverAbility0(GameState state)
		{
			super(state, "Sliver creatures you control have \"This creature can't be blocked except by two or more creatures.\"");
			this.addEffectPart(addAbilityToObject(SLIVER_CREATURES_YOU_CONTROL, Belligerent.class));
		}
	}

	public BelligerentSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sliver creatures you control have
		// "This creature can't be blocked except by two or more creatures."
		this.addAbility(new BelligerentSliverAbility0(state));
	}
}
