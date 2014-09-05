package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Jet Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Tempest.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class JetMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public JetMedallion(GameState state)
	{
		super(state, Color.BLACK);
	}
}
