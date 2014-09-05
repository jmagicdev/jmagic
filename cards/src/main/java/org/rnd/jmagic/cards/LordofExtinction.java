package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lord of Extinction")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3BG")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class LordofExtinction extends Card
{
	public static final class StrengthOfDecay extends CharacteristicDefiningAbility
	{
		public StrengthOfDecay(GameState state)
		{
			super(state, "Lord of Extinction's power and toughness are each equal to the number of cards in all graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(InZone.instance(GraveyardOf.instance(Players.instance())));

			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public LordofExtinction(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new StrengthOfDecay(state));
	}
}
