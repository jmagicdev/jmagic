package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peer Through Depths")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PeerThroughDepths extends Card
{
	public PeerThroughDepths(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.CARD, TopCards.instance(5, LibraryOf.instance(You.instance())));
		parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.INSTANT, Type.SORCERY));
		this.addEffect(new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, parameters, "Look at the top five cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
	}
}
