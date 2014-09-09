package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burning Wish")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class BurningWish extends Card
{
	public BurningWish(GameState state)
	{
		super(state);

		// You may choose a sorcery card you own from outside the game, reveal
		// that card, and put it into your hand. Exile Burning Wish.

		EventFactory chooseFactory = new EventFactory(EventType.WISH, "Choose a sorcery card you own from outside the game, reveal that card, and put it into your hand.");
		chooseFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		chooseFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasType.instance(Type.SORCERY), CardsYouOwnOutsideTheGame.instance()));
		chooseFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		this.addEffect(playerMay(You.instance(), chooseFactory, "You may choose a sorcery card you own from outside the game, reveal that card, and put it into your hand."));

		this.addEffect(exile(This.instance(), "Exile Burning Wish."));
	}
}
