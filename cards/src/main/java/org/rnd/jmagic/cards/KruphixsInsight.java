package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kruphix's Insight")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class KruphixsInsight extends Card
{
	public KruphixsInsight(GameState state)
	{
		super(state);

		// Reveal the top six cards of your library. Put up to three enchantment
		// cards from among them into your hand and the rest of the revealed
		// cards into your graveyard.
		this.addEffect(Sifter.start().reveal(6).take(3, HasType.instance(Type.ENCHANTMENT)).dumpToGraveyard().getEventFactory("Reveal the top six cards of your library. Put up to three enchantment cards from among them into your hand and the rest of the revealed cards into your graveyard."));
	}
}
