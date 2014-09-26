package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mystic Penitent")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MYSTIC, SubType.NOMAD})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class MysticPenitent extends Card
{
	public static final class ThresholdPenance extends StaticAbility
	{
		public ThresholdPenance(GameState state)
		{
			super(state, "Threshold \u2014 As long as seven or more cards are in your graveyard, Mystic Penitent gets +1/+1 and has flying.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public MysticPenitent(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new ThresholdPenance(state));
	}
}
