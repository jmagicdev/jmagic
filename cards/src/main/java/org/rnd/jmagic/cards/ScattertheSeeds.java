package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Scatter the Seeds")
@Types({Type.INSTANT})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class ScattertheSeeds extends Card
{
	public ScattertheSeeds(GameState state)
	{
		super(state);

		// Convoke
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Put three 1/1 green Saproling creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 green Saproling creature tokens onto the battlefield.");
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.SAPROLING);
		this.addEffect(tokens.getEventFactory());
	}
}
