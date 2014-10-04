package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Squelching Leeches")
@Types({Type.CREATURE})
@SubTypes({SubType.LEECH})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class SquelchingLeeches extends Card
{
	public static final class SquelchingLeechesAbility0 extends CharacteristicDefiningAbility
	{
		public SquelchingLeechesAbility0(GameState state)
		{
			super(state, "Squelching Leeches's power and toughness are each equal to the number of Swamps you control.",//
			Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(ControlledBy.instance(ControllerOf.instance(This.instance())), HasSubType.instance(SubType.SWAMP)));
			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public SquelchingLeeches(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Squelching Leeches's power and toughness are each equal to the number
		// of Swamps you control.
		this.addAbility(new SquelchingLeechesAbility0(state));
	}
}
