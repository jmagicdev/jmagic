package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Call of the Conclave")
@Types({Type.SORCERY})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CalloftheConclave extends Card
{
	public CalloftheConclave(GameState state)
	{
		super(state);

		// Put a 3/3 green Centaur creature token onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Centaur creature token onto the battlefield.");
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.CENTAUR);
		this.addEffect(factory.getEventFactory());
	}
}
