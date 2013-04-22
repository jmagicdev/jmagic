package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ruby Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({})
public final class RubyMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public RubyMedallion(GameState state)
	{
		super(state, Color.RED);
	}
}
