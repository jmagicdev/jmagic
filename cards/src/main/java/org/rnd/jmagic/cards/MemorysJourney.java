package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Memory's Journey")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class MemorysJourney extends Card
{
	public MemorysJourney(GameState state)
	{
		super(state);

		// Target player shuffles up to three target cards from his or her
		// graveyard into his or her library.
		SetGenerator targetPlayer = targetedBy(this.addTarget(Players.instance(), "target player"));
		Target targetCards = this.addTarget(InZone.instance(GraveyardOf.instance(targetPlayer)), "up to three target cards in his or her graveyard");
		targetCards.setNumber(0, 3);

		EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Target player shuffles up to three target cards from his or her graveyard into his or her library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(targetedBy(targetCards), targetPlayer));
		this.addEffect(factory);

		// Flashback (G) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(G)"));
	}
}
