package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Veinfire Borderpost")
@ManaCost("1BR")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class VeinfireBorderpost extends Borderpost
{
	public VeinfireBorderpost(GameState state)
	{
		super(state, Color.BLACK, Color.RED);
	}
}
