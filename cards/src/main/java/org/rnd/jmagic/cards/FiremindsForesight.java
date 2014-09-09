package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firemind's Foresight")
@Types({Type.INSTANT})
@ManaCost("5UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class FiremindsForesight extends Card
{
	public FiremindsForesight(GameState state)
	{
		super(state);

		// Search your library for an instant card with converted mana cost 3,
		// reveal it, and put it into your hand. Then repeat this process for
		// instant cards with converted mana costs 2 and 1. Then shuffle your
		// library.

		String[] names = new String[] {"Search your library for an instant card with converted mana cost 3, reveal it, and put it into your hand.", "Then repeat this process for instant cards with converted mana costs 2", "and 1."};

		for(int i = 0; i < names.length; ++i)
		{
			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, names[i]);
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasType.instance(Type.INSTANT), HasConvertedManaCost.instance(numberGenerator(3 - i)))));
			this.addEffect(factory);
		}

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
