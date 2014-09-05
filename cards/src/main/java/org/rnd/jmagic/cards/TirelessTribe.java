package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tireless Tribe")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.NOMAD})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class TirelessTribe extends Card
{
	public static final class TirelessTribeAbility0 extends ActivatedAbility
	{
		public TirelessTribeAbility0(GameState state)
		{
			super(state, "Discard a card: Tireless Tribe gets +0/+4 until end of turn.");
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +0, +4, "Tireless Tribe gets +0/+4 until end of turn."));
		}
	}

	public TirelessTribe(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Discard a card: Tireless Tribe gets +0/+4 until end of turn.
		this.addAbility(new TirelessTribeAbility0(state));
	}
}
