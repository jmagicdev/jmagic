package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Captain's Call")
@Types({Type.SORCERY})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CaptainsCall extends Card
{
	public CaptainsCall(GameState state)
	{
		super(state);

		// Put three 1/1 white Soldier creature tokens onto the battlefield.
		CreateTokensFactory token = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Soldier creature tokens onto the battlefield.");
		token.setColors(Color.WHITE);
		token.setSubTypes(SubType.SOLDIER);
		this.addEffect(token.getEventFactory());
	}
}
