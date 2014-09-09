package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mitotic Manipulation")
@Types({Type.SORCERY})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class MitoticManipulation extends Card
{
	public MitoticManipulation(GameState state)
	{
		super(state);

		// Look at the top seven cards of your library.
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		SetGenerator topSeven = TopCards.instance(7, yourLibrary);
		EventFactory look = new EventFactory(EventType.LOOK, "Look at the top seven cards of your library.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		look.parameters.put(EventType.Parameter.OBJECT, topSeven);
		this.addEffect(look);

		SetGenerator filteredCards = Intersect.instance(HasName.instance(NameOf.instance(Permanents.instance())), topSeven);

		// You may put one of those cards onto the battlefield if it has the
		// same name as a permanent.
		EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "You may put one of those cards onto the battlefield if it has the same name as a permanent.");
		putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, filteredCards);
		this.addEffect(youMay(putOntoBattlefield, "You may put one of those cards onto the battlefield if it has the same name as a permanent."));

		// Put the rest on the bottom of your library in any order.
		EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put the rest on the bottom of your library in any order.");
		putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		putOnBottom.parameters.put(EventType.Parameter.OBJECT, topSeven);
		this.addEffect(putOnBottom);
	}
}
