package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Sprout")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Sprout extends Card
{
	public Sprout(GameState state)
	{
		super(state);

		CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield.");
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.SAPROLING);
		this.addEffect(token.getEventFactory());
	}
}
