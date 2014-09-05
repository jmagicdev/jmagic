package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Slate Street Ruffian")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SlateStreetRuffian extends Card
{
	public static final class SlateStreetRuffianAbility0 extends EventTriggeredAbility
	{
		public SlateStreetRuffianAbility0(GameState state)
		{
			super(state, "Whenever Slate Street Ruffian becomes blocked, defending player discards a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(discardCards(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS), 1, "Defending player discards a card."));
		}
	}

	public SlateStreetRuffian(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Slate Street Ruffian becomes blocked, defending player
		// discards a card.
		this.addAbility(new SlateStreetRuffianAbility0(state));
	}
}
