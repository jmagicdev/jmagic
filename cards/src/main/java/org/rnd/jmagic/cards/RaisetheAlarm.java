package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Raise the Alarm")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RaisetheAlarm extends Card
{
	public RaisetheAlarm(GameState state)
	{
		super(state);

		CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Soldier creature tokens onto the battlefield.");
		tokens.setColors(Color.WHITE);
		tokens.setSubTypes(SubType.SOLDIER);
		this.addEffect(tokens.getEventFactory());
	}
}
