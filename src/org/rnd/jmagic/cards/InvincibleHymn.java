package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Invincible Hymn")
@Types({Type.SORCERY})
@ManaCost("6WW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class InvincibleHymn extends Card
{
	public InvincibleHymn(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(EventType.SET_LIFE, "Count the number of cards in your library. Your life total becomes that number.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(InZone.instance(LibraryOf.instance(You.instance()))));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
