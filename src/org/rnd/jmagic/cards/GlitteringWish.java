package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glittering Wish")
@Types({Type.SORCERY})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GlitteringWish extends Card
{
	public GlitteringWish(GameState state)
	{
		super(state);

		EventFactory chooseFactory = new EventFactory(EventType.WISH, "Choose a multicolored card you own from outside the game, reveal that card, and put it into your hand.");
		chooseFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		chooseFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Multicolored.instance(), CardsYouOwnOutsideTheGame.instance()));
		chooseFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		this.addEffect(playerMay(You.instance(), chooseFactory, "You may choose a multicolored card you own from outside the game, reveal that card, and put it into your hand."));

		this.addEffect(exile(This.instance(), "Exile Glittering Wish."));
	}
}
