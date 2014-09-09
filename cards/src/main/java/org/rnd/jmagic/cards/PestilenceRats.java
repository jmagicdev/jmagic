package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pestilence Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class PestilenceRats extends Card
{
	public static final class PestilenceRatsAbility0 extends CharacteristicDefiningAbility
	{
		public PestilenceRatsAbility0(GameState state)
		{
			super(state, "Pestilence Rats's power is equal to the number of other Rats on the battlefield.", Characteristics.Characteristic.POWER);

			SetGenerator rats = Intersect.instance(HasSubType.instance(SubType.RAT), InZone.instance(Battlefield.instance()));
			SetGenerator otherRats = RelativeComplement.instance(rats, This.instance());
			this.addEffectPart(setPowerAndToughness(This.instance(), null, Count.instance(otherRats)));
		}
	}

	public PestilenceRats(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Pestilence Rats's power is equal to the number of other Rats on the
		// battlefield. (For example, as long as there are two other Rats on the
		// battlefield, Pestilence Rats's power and toughness are 2/3.)
		this.addAbility(new PestilenceRatsAbility0(state));
	}
}
