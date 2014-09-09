package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stalwart Shield-Bearers")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class StalwartShieldBearers extends Card
{
	public static final class StalwartShieldBearersAbility1 extends StaticAbility
	{
		public StalwartShieldBearersAbility1(GameState state)
		{
			super(state, "Other creatures you control with defender get +0/+2.");

			SetGenerator defenders = Intersect.instance(CREATURES_YOU_CONTROL, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class));
			SetGenerator otherDefenders = RelativeComplement.instance(defenders, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherDefenders, +0, +2));
		}
	}

	public StalwartShieldBearers(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Other creatures you control with defender get +0/+2.
		this.addAbility(new StalwartShieldBearersAbility1(state));
	}
}
