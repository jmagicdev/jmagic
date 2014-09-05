package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Nacatl Outlander")
@ManaCost("RG")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SCOUT})
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class NacatlOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public NacatlOutlander(GameState state)
	{
		super(state, Color.BLUE);
	}
}
