package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sultai Flayer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.NAGA})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class SultaiFlayer extends Card
{
	public static final class SultaiFlayerAbility0 extends EventTriggeredAbility
	{
		public SultaiFlayerAbility0(GameState state)
		{
			super(state, "Whenever a creature you control with toughness 4 or greater dies, you gain 4 life.");

			SetGenerator bigGuys = Intersect.instance(CREATURES_YOU_CONTROL, HasToughness.instance(Between.instance(4, null)));
			this.addPattern(whenXDies(bigGuys));
			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public SultaiFlayer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Whenever a creature you control with toughness 4 or greater dies, you
		// gain 4 life.
		this.addAbility(new SultaiFlayerAbility0(state));
	}
}
