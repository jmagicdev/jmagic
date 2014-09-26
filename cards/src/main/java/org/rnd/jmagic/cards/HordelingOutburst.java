package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Hordeling Outburst")
@Types({Type.SORCERY})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class HordelingOutburst extends Card
{
	public HordelingOutburst(GameState state)
	{
		super(state);

		// Put three 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory goblins = new CreateTokensFactory(3, 1, 1, "Put three 1/1 red Goblin creature tokens onto the battlefield.");
		goblins.setColors(Color.RED);
		goblins.setSubTypes(SubType.GOBLIN);
		this.addEffect(goblins.getEventFactory());
	}
}
