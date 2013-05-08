package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crusader of Odric")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CrusaderofOdric extends Card
{
	public static final class CrusaderofOdricAbility0 extends CharacteristicDefiningAbility
	{
		public CrusaderofOdricAbility0(GameState state)
		{
			super(state, "Crusader of Odric's power and toughness are each equal to the number of creatures you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator num = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffectPart(setPowerAndToughness(This.instance(), num, num));
		}
	}

	public CrusaderofOdric(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Crusader of Odric's power and toughness are each equal to the number
		// of creatures you control.
		this.addAbility(new CrusaderofOdricAbility0(state));
	}
}
