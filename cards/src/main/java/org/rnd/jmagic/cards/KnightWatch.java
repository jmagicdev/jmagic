package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Knight Watch")
@Types({Type.SORCERY})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightWatch extends Card
{
	public KnightWatch(GameState state)
	{
		super(state);

		// Put two 2/2 white Knight creature tokens with vigilance onto the
		// battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 2, 2, "Put two 2/2 white Knight creature tokens with vigilance onto the battlefield.");
		factory.setColors(Color.WHITE);
		factory.setSubTypes(SubType.KNIGHT);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
		this.addEffect(factory.getEventFactory());
	}
}
