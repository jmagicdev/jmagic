package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tarmogoyf")
@Types({Type.CREATURE})
@SubTypes({SubType.LHURGOYF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Tarmogoyf extends Card
{
	public static final class TarmogoyfCDA extends CharacteristicDefiningAbility
	{
		public TarmogoyfCDA(GameState state)
		{
			super(state, "Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator cardsInGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator cardTypesInGraveyards = Count.instance(CardTypeOf.instance(cardsInGraveyards));

			this.addEffectPart(setPowerAndToughness(This.instance(), cardTypesInGraveyards, Sum.instance(Union.instance(numberGenerator(1), cardTypesInGraveyards))));
		}
	}

	public Tarmogoyf(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		this.addAbility(new TarmogoyfCDA(state));
	}
}
