package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peer Through Depths")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class PeerThroughDepths extends Card
{
	public PeerThroughDepths(GameState state)
	{
		super(state);

		SetGenerator spellCards = HasType.instance(Type.INSTANT, Type.SORCERY);
		this.addEffect(Sifter.start().look(5).take(1, spellCards).dumpToBottom().getEventFactory("Look at the top five cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
	}
}
