package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rise of Eagles")
@Types({Type.SORCERY})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class RiseofEagles extends Card
{
	public RiseofEagles(GameState state)
	{
		super(state);

		// Put two 2/2 blue Bird enchantment creature tokens with flying onto
		// the battlefield.
		CreateTokensFactory birds = new CreateTokensFactory(2, 2, 2, "Put two 2/2 blue Bird enchantment creature tokens with flying onto the battlefield.");
		birds.setColors(Color.BLUE);
		birds.setSubTypes(SubType.BIRD);
		birds.setEnchantment();
		this.addEffect(birds.getEventFactory());

		// Scry 1.
		this.addEffect(scry(1, "Scry 1."));
	}
}
