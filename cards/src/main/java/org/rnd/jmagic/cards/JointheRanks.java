package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Join the Ranks")
@Types({Type.INSTANT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class JointheRanks extends Card
{
	public JointheRanks(GameState state)
	{
		super(state);

		// Put two 1/1 white Soldier Ally creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Soldier Ally creature tokens onto the battlefield.");
		tokens.setColors(Color.WHITE);
		tokens.setSubTypes(SubType.SOLDIER, SubType.ALLY);
		this.addEffect(tokens.getEventFactory());
	}
}
