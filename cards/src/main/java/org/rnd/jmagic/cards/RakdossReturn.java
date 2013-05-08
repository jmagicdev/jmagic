package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rakdos's Return")
@Types({Type.SORCERY})
@ManaCost("XBR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdossReturn extends Card
{
	public RakdossReturn(GameState state)
	{
		super(state);

		// Rakdos's Return deals X damage to target opponent. That player
		// discards X cards.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		SetGenerator X = ValueOfX.instance(This.instance());
		this.addEffect(spellDealDamage(X, target, "Rakdos's Return deals X damage to target opponent."));
		this.addEffect(discardCards(target, X, "That player discards X cards."));
	}
}
