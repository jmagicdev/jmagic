package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lead the Stampede")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class LeadtheStampede extends Card
{
	public LeadtheStampede(GameState state)
	{
		super(state);

		// Look at the top five cards of your library. You may reveal any number
		// of creature cards from among them and put the revealed cards into
		// your hand. Put the rest on the bottom of your library in any order.

		EventFactory look = new EventFactory(EventType.LOOK, "Look at the top five cards of your library.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(5, LibraryOf.instance(You.instance())));
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(look);

		SetGenerator looked = EffectResult.instance(look);
		SetGenerator creaturesLooked = Intersect.instance(looked, HasType.instance(Type.CREATURE));

		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal any number of creature cards from among them");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, creaturesLooked);
		reveal.parameters.put(EventType.Parameter.NUMBER, Between.instance(numberGenerator(0), Count.instance(creaturesLooked)));
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		SetGenerator revealed = EffectResult.instance(reveal);

		EventFactory pickUp = new EventFactory(EventType.MOVE_OBJECTS, "put the revealed cards into your hand.");
		pickUp.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pickUp.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		pickUp.parameters.put(EventType.Parameter.OBJECT, revealed);

		this.addEffect(youMay(sequence(reveal, pickUp), "You may reveal any number of creature cards from among them and put the revealed cards into your hand."));

		EventFactory putBack = new EventFactory(EventType.MOVE_OBJECTS, "Put the rest on the bottom of your library in any order.");
		putBack.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putBack.parameters.put(EventType.Parameter.TO, LibraryOf.instance(You.instance()));
		putBack.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		putBack.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(looked, revealed));
		this.addEffect(putBack);
	}
}
