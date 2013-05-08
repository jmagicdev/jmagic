package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Maro")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Maro extends Card
{
	public static final class MaroAbility0 extends CharacteristicDefiningAbility
	{
		public MaroAbility0(GameState state)
		{
			super(state, "Maro's power and toughness are each equal to the number of cards in your hand.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public Maro(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Maro's power and toughness are each equal to the number of cards in
		// your hand.
		this.addAbility(new MaroAbility0(state));
	}
}
