package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Call of the Nightwing")
@Types({Type.SORCERY})
@ManaCost("2UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class CalloftheNightwing extends Card
{
	public CalloftheNightwing(GameState state)
	{
		super(state);

		// Put a 1/1 blue and black Horror creature token with flying onto the
		// battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue and black Horror creature token with flying onto the battlefield.");
		factory.setColors(Color.BLACK, Color.BLUE);
		factory.setSubTypes(SubType.HORROR);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(factory.getEventFactory());

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
