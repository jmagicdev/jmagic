package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Goblin Rally")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinRally extends Card
{
	public GoblinRally(GameState state)
	{
		super(state);

		// Put four 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(4, 1, 1, "Put four 1/1 red Goblin creature tokens onto the battlefield.");
		tokens.setColors(Color.RED);
		tokens.setSubTypes(SubType.GOBLIN);
		this.addEffect(tokens.getEventFactory());
	}
}
