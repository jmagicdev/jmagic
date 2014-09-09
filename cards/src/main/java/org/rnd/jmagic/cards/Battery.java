package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Battery")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class Battery extends Card
{
	public Battery(GameState state)
	{
		super(state);

		// Put a 3/3 green Elephant creature token onto the battlefield.
		CreateTokensFactory elephant = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Elephant creature token onto the battlefield.");
		elephant.setColors(Color.GREEN);
		elephant.setSubTypes(SubType.ELEPHANT);
		this.addEffect(elephant.getEventFactory());
	}
}
