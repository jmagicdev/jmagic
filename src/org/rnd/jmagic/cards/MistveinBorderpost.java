package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Mistvein Borderpost")
@ManaCost("1UB")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class MistveinBorderpost extends Borderpost
{
	public MistveinBorderpost(GameState state)
	{
		super(state, Color.BLUE, Color.BLACK);
	}
}
