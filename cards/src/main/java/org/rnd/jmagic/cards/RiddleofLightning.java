package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riddle of Lightning")
@Types({Type.INSTANT})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class RiddleofLightning extends Card
{
	public RiddleofLightning(GameState state)
	{
		super(state);

		// Choose target creature or player. Scry 3,
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(scry(3, "Choose target creature or player. Scry 3,"));

		// then reveal the top card of your library.
		SetGenerator top = TopCards.instance(1, LibraryOf.instance(You.instance()));
		this.addEffect(reveal(top, "then reveal the top card of your library."));

		// Riddle of Lightning deals damage equal to that card's converted mana
		// cost to that creature or player.
		SetGenerator amount = ConvertedManaCostOf.instance(top);
		this.addEffect(spellDealDamage(amount, target, "Riddle of Lightning deals damage equal to that card's converted mana cost to that creature or player."));
	}
}
