package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Inescapable Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.WARRIOR})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class InescapableBrute extends Card
{
	public static final class Trap extends StaticAbility
	{
		public Trap(GameState state)
		{
			super(state, "Inescapable Brute must be blocked if able.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
			this.addEffectPart(part);
		}
	}

	public InescapableBrute(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Wither(state));

		this.addAbility(new Trap(state));
	}
}
