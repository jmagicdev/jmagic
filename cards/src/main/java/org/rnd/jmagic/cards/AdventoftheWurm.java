package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Advent of the Wurm")
@Types({Type.INSTANT})
@ManaCost("1GGW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class AdventoftheWurm extends Card
{
	public AdventoftheWurm(GameState state)
	{
		super(state);

		// Put a 5/5 green Wurm creature token with trample onto the
		// battlefield.
		CreateTokensFactory wurm = new CreateTokensFactory(1, 5, 5, "Put a 5/5 green Wurm creature token with trample onto the battlefield.");
		wurm.setColors(Color.GREEN);
		wurm.setSubTypes(SubType.WURM);
		wurm.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
		this.addEffect(wurm.getEventFactory());
	}
}
