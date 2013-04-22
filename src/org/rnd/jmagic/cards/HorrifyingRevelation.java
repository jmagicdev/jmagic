package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horrifying Revelation")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HorrifyingRevelation extends Card
{
	public HorrifyingRevelation(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		// Target player discards a card, then puts the top card of his or her
		// library into his or her graveyard.
		this.addEffect(discardCards(target, 1, "Target player discards a card,"));
		this.addEffect(millCards(target, 1, "then puts the top card of his or her library into his or her graveyard."));
	}
}
