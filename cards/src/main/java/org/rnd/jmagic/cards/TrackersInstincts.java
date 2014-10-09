package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tracker's Instincts")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class TrackersInstincts extends Card
{
	public TrackersInstincts(GameState state)
	{
		super(state);

		// Reveal the top four cards of your library. Put a creature card from
		// among them into your hand and the rest into your graveyard.
		this.addEffect(Sifter.start().reveal(4).take(1, HasType.instance(Type.CREATURE)).dumpToGraveyard().getEventFactory("Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard."));

		// Flashback (2)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(U)"));
	}
}
