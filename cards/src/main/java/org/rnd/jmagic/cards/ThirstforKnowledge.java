package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thirst for Knowledge")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ThirstforKnowledge extends Card
{
	public ThirstforKnowledge(GameState state)
	{
		super(state);

		// Draw three cards.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));

		// Then discard two cards unless you discard an artifact card.
		EventFactory discardArtifact = new EventFactory(EventType.DISCARD_CHOICE, "Discard an artifact card");
		discardArtifact.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discardArtifact.parameters.put(EventType.Parameter.PLAYER, You.instance());
		discardArtifact.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		discardArtifact.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.ARTIFACT)));

		EventFactory discardTwo = discardCards(You.instance(), 2, "Discard two cards");

		EventFactory effect = unless(You.instance(), discardTwo, discardArtifact, "Then discard two cards unless you discard an artifact card.");
		this.addEffect(effect);
	}
}
