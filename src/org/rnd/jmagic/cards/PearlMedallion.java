package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pearl Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({})
public final class PearlMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public PearlMedallion(GameState state)
	{
		super(state, Color.WHITE);
	}
}
