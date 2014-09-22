package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Signal the Clans")
@Types({Type.INSTANT})
@ManaCost("RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class SignaltheClans extends Card
{
	public SignaltheClans(GameState state)
	{
		super(state);

		// Search your library for three creature cards and reveal them.
		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for three creature cards and reveal them.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));
		this.addEffect(search);

		// If you reveal three cards with different names, choose one of them at
		// random and put that card into your hand.
		SetGenerator revealedCards = Intersect.instance(EffectResult.instance(search), Cards.instance());
		SetGenerator threeNames = Intersect.instance(numberGenerator(3), Count.instance(NameOf.instance(revealedCards)));

		EventFactory random = new EventFactory(RANDOM, "Choose one of them at random");
		random.parameters.put(EventType.Parameter.OBJECT, revealedCards);
		SetGenerator chosen = EffectResult.instance(random);

		EventFactory toHand = putIntoHand(chosen, You.instance(), "and put that card into your hand");

		// The three names condition is sufficient, since if you search for less
		// than three cards, there will be less than three names (and you can't
		// search for more)
		EventFactory effect = ifThen(threeNames, sequence(random, toHand), "If you reveal three cards with different names, choose one of them at random and put that card into your hand.");
		this.addEffect(effect);

		// Shuffle the rest into your library.
		this.addEffect(shuffleLibrary(You.instance(), "Shuffle the rest into your library."));
	}
}
