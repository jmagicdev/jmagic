package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seraph of the Masses")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class SeraphoftheMasses extends Card
{
	public static final class SeraphoftheMassesAbility2 extends CharacteristicDefiningAbility
	{
		public SeraphoftheMassesAbility2(GameState state)
		{
			super(state, "Seraph of the Masses's power and toughness are each equal to the number of creatures you control.",//
			Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public SeraphoftheMasses(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Seraph of the Masses's power and toughness are each equal to the
		// number of creatures you control.
		this.addAbility(new SeraphoftheMassesAbility2(state));
	}
}
