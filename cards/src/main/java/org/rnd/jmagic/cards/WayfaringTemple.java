package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wayfaring Temple")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class WayfaringTemple extends Card
{
	public static final class WayfaringTempleAbility0 extends CharacteristicDefiningAbility
	{
		public WayfaringTempleAbility0(GameState state)
		{
			super(state, "Wayfaring Temple's power and toughness are each equal to the number of creatures you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator num = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffectPart(setPowerAndToughness(This.instance(), num, num));
		}
	}

	public static final class WayfaringTempleAbility1 extends EventTriggeredAbility
	{
		public WayfaringTempleAbility1(GameState state)
		{
			super(state, "Whenever Wayfaring Temple deals combat damage to a player, populate.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			this.addEffect(populate());
		}
	}

	public WayfaringTemple(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Wayfaring Temple's power and toughness are each equal to the number
		// of creatures you control.
		this.addAbility(new WayfaringTempleAbility0(state));

		// Whenever Wayfaring Temple deals combat damage to a player, populate.
		// (Put a token onto the battlefield that's a copy of a creature token
		// you control.)
		this.addAbility(new WayfaringTempleAbility1(state));
	}
}
