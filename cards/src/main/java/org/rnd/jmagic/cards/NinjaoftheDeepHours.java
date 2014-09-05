package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ninja of the Deep Hours")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.NINJA})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class NinjaoftheDeepHours extends Card
{
	public static final class NinjaDraw extends EventTriggeredAbility
	{
		public NinjaDraw(GameState state)
		{
			super(state, "Whenever Ninja of the Deep Hours deals combat damage to a player, you may draw a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public NinjaoftheDeepHours(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Ninjutsu(state, "(1)(U)"));
		this.addAbility(new NinjaDraw(state));
	}
}
