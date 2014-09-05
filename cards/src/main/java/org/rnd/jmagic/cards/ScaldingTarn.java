package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Scalding Tarn")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ScaldingTarn extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public ScaldingTarn(GameState state)
	{
		super(state, SubType.ISLAND, SubType.MOUNTAIN);
	}
}
