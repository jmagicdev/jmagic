package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Scroll Thief")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.ROGUE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ScrollThief extends Card
{
	public static final class ScrollThiefAbility0 extends EventTriggeredAbility
	{
		public ScrollThiefAbility0(GameState state)
		{
			super(state, "Whenever Scroll Thief deals combat damage to a player, draw a card.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));
			this.addEffect(drawACard());
		}
	}

	public ScrollThief(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Whenever Scroll Thief deals combat damage to a player, draw a card.
		this.addAbility(new ScrollThiefAbility0(state));
	}
}
