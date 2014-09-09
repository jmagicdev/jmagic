package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cunning Wish")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class CunningWish extends Card
{
	public CunningWish(GameState state)
	{
		super(state);

		EventFactory chooseFactory = new EventFactory(EventType.WISH, "Choose an instant card you own from outside the game, reveal that card, and put it into your hand.");
		chooseFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		chooseFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasType.instance(Type.INSTANT), CardsYouOwnOutsideTheGame.instance()));
		chooseFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		this.addEffect(playerMay(You.instance(), chooseFactory, "You may choose an instant card you own from outside the game, reveal that card, and put it into your hand."));

		this.addEffect(exile(This.instance(), "Exile Cunning Wish."));
	}
}
