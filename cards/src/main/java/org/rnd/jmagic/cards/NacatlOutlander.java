package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nacatl Outlander")
@ManaCost("RG")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SCOUT})
@ColorIdentity({Color.RED, Color.GREEN})
public final class NacatlOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public NacatlOutlander(GameState state)
	{
		super(state, Color.BLUE);
	}
}
