package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Krenko's Command")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KrenkosCommand extends Card
{
	public KrenkosCommand(GameState state)
	{
		super(state);

		// Put two 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 red Goblin creature tokens onto the battlefield.");
		factory.setColors(Color.RED);
		factory.setSubTypes(SubType.GOBLIN);
		this.addEffect(factory.getEventFactory());
	}
}
