package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blasted Landscape")
@Types({org.rnd.jmagic.engine.Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BlastedLandscape extends org.rnd.jmagic.engine.Card
{
	public BlastedLandscape(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
