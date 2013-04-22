package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Dragon Fodder")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class DragonFodder extends Card
{
	public DragonFodder(GameState state)
	{
		super(state);

		// Put two 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 red Goblin creature tokens onto the battlefield.");
		tokens.setColors(Color.RED);
		tokens.setSubTypes(SubType.GOBLIN);
		this.addEffect(tokens.getEventFactory());
	}
}
