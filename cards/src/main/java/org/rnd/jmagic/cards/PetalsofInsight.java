package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Petals of Insight")
@Types({Type.SORCERY})
@SubTypes({SubType.ARCANE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class PetalsofInsight extends Card
{
	public PetalsofInsight(GameState state)
	{
		super(state);

		// Look at the top three cards of your library.
		SetGenerator topThree = TopCards.instance(3, LibraryOf.instance(You.instance()));

		EventFactory look = new EventFactory(EventType.LOOK, "Look at the top three cards of your library.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.OBJECT, topThree);
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(look);

		// You may put those cards on the bottom of your library in any order.
		EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put those cards on the bottom of your library in any order.");
		putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOnBottom.parameters.put(EventType.Parameter.OBJECT, topThree);
		putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));

		// If you do, return Petals of Insight to its owner's hand.
		EventFactory returnPetals = new EventFactory(EventType.MOVE_OBJECTS, "Return Petals of Insight to its owner's hand.");
		returnPetals.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnPetals.parameters.put(EventType.Parameter.OBJECT, This.instance());
		returnPetals.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(This.instance())));

		// Otherwise, draw three cards.

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put those cards on the bottom of your library in any order. If you do, return Petals of Insight to its owner's hand. Otherwise, draw three cards.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(putOnBottom, "You may put those cards on the bottom of your library in any order.")));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(returnPetals));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(drawCards(You.instance(), 3, "Draw three cards.")));
		this.addEffect(effect);
	}
}
