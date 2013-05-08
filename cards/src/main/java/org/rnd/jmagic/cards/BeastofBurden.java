package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beast of Burden")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PROMO, r = Rarity.RARE)})
@ColorIdentity({})
public final class BeastofBurden extends Card
{
	public static final class BeastofBurdenAbility0 extends CharacteristicDefiningAbility
	{
		public BeastofBurdenAbility0(GameState state)
		{
			super(state, "Beast of Burden's power and toughness are each equal to the number of creatures on the battlefield.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(CreaturePermanents.instance());
			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public BeastofBurden(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Beast of Burden's power and toughness are each equal to the number of
		// creatures on the battlefield.
		this.addAbility(new BeastofBurdenAbility0(state));
	}
}
