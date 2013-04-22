package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Thieving Magpie")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ThievingMagpie extends Card
{
	public static final class Steal extends EventTriggeredAbility
	{
		public Steal(GameState state)
		{
			super(state, "Whenever Thieving Magpie deals damage to an opponent, draw a card.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			this.addEffect(drawACard());
		}
	}

	public ThievingMagpie(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Steal(state));
	}
}
