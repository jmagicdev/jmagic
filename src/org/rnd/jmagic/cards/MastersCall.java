package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Master's Call")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MastersCall extends Card
{
	public MastersCall(GameState state)
	{
		super(state);

		// Put two 1/1 colorless Myr artifact creature tokens onto the
		// battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 colorless Myr artifact creature tokens onto the battlefield.");
		factory.setSubTypes(SubType.MYR);
		factory.setArtifact();
		this.addEffect(factory.getEventFactory());
	}
}
