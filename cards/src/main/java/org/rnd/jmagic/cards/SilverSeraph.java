package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silver Seraph")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WWW")
@ColorIdentity({Color.WHITE})
public final class SilverSeraph extends Card
{
	public static final class ThresholdPenance extends StaticAbility
	{
		public ThresholdPenance(GameState state)
		{
			super(state, "Threshold \u2014 Other creatures you control get +2/+2 as long as seven or more cards are in your graveyard.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), +2, +2));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public SilverSeraph(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new ThresholdPenance(state));
	}
}
