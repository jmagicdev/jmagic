package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thieving Magpie")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.UNCOMMON)})
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
