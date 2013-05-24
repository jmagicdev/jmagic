package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Firewild Borderpost")
@ManaCost("1RG")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FirewildBorderpost extends Borderpost
{
	public FirewildBorderpost(GameState state)
	{
		super(state, Color.RED, Color.GREEN);
	}
}
