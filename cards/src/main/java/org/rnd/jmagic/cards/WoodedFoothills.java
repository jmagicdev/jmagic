package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wooded Foothills")
@Types({Type.LAND})
@ColorIdentity({})
public final class WoodedFoothills extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public WoodedFoothills(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.FOREST);
	}
}
