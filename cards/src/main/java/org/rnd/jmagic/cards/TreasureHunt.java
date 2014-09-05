package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Treasure Hunt")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TreasureHunt extends Card
{
	public TreasureHunt(GameState state)
	{
		super(state);

		// Reveal cards from the top of your library until you reveal a nonland
		// card, then put all cards revealed this way into your hand.
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		SetGenerator nonlandsInYourLibrary = RelativeComplement.instance(InZone.instance(yourLibrary), HasType.instance(Type.LAND));
		SetGenerator toReveal = TopMost.instance(yourLibrary, numberGenerator(1), nonlandsInYourLibrary);

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal cards from the top of your library until you reveal a nonland card,");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, toReveal);
		this.addEffect(reveal);

		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "then put all cards revealed this way into your hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.OBJECT, toReveal);
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(move);
	}
}
