package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shadow of Doubt")
@Types({Type.INSTANT})
@ManaCost("(U/B)(U/B)")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ShadowofDoubt extends Card
{
	public ShadowofDoubt(GameState state)
	{
		super(state);

		// Players can't search libraries this turn.
		SimpleEventPattern searching = new SimpleEventPattern(EventType.SEARCH_MARKER);
		searching.put(EventType.Parameter.CARD, LibraryOf.instance(Players.instance()));

		ContinuousEffect.Part noSearch = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		noSearch.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(searching));
		this.addEffect(createFloatingEffect("Players can't search libraries this turn.", noSearch));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
