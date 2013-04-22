package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wheel of Fortune")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WheelofFortune extends Card
{
	public WheelofFortune(GameState state)
	{
		super(state);

		this.addEffect(discardHand(Players.instance(), "Each player discards his or her hand"));

		this.addEffect(drawCards(Players.instance(), 7, "and draws seven cards"));
	}
}
