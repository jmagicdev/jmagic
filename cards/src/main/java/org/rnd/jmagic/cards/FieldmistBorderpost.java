package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fieldmist Borderpost")
@ManaCost("1WU")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class FieldmistBorderpost extends Borderpost
{
	public FieldmistBorderpost(GameState state)
	{
		super(state, Color.WHITE, Color.BLUE);
	}
}
