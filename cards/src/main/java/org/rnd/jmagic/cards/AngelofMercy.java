package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel of Mercy")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class AngelofMercy extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "When Angel of Mercy enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public AngelofMercy(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new GainLife(state));
	}
}
