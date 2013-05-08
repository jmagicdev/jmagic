package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battle of Wits")
@Types({Type.ENCHANTMENT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class BattleofWits extends Card
{
	public static final class BattleofWitsAbility0 extends EventTriggeredAbility
	{
		public BattleofWitsAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you have 200 or more cards in your library, you win the game.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Intersect.instance(Count.instance(InZone.instance(LibraryOf.instance(You.instance()))), Between.instance(200, null));

			this.addEffect(youWinTheGame());
		}
	}

	public BattleofWits(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, if you have 200 or more cards in
		// your library, you win the game.
		this.addAbility(new BattleofWitsAbility0(state));
	}
}
