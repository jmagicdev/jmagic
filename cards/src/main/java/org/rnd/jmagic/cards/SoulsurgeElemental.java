package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soulsurge Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SoulsurgeElemental extends Card
{
	public static final class SoulsurgePower extends CharacteristicDefiningAbility
	{
		public SoulsurgePower(GameState state)
		{
			super(state, "Soulsurge Elemental's power is equal to the number of creatures you control.", Characteristics.Characteristic.POWER);

			this.addEffectPart(setPowerAndToughness(This.instance(), Count.instance(CREATURES_YOU_CONTROL), null));
		}
	}

	public SoulsurgeElemental(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Soulsurge Elemental's power is equal to the number of creatures you
		// control.
		this.addAbility(new SoulsurgePower(state));
	}
}
