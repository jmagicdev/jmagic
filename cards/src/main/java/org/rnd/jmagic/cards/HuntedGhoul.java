package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunted Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class HuntedGhoul extends Card
{
	public static final class HuntedGhoulAbility0 extends StaticAbility
	{
		public HuntedGhoulAbility0(GameState state)
		{
			super(state, "Hunted Ghoul can't block Humans.");

			SetGenerator restriction = Intersect.instance(HasSubType.instance(SubType.HUMAN), BlockedBy.instance(This.instance()));
			ContinuousEffect.Part cantBlock = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			cantBlock.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(cantBlock);
		}
	}

	public HuntedGhoul(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Hunted Ghoul can't block Humans.
		this.addAbility(new HuntedGhoulAbility0(state));
	}
}
