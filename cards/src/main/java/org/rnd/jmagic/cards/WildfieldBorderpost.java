package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Wildfield Borderpost")
@ManaCost("1GW")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class WildfieldBorderpost extends Borderpost
{
	public WildfieldBorderpost(GameState state)
	{
		super(state, Color.GREEN, Color.WHITE);
	}
}
