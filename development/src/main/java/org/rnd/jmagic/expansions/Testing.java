package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Testing")
public final class Testing extends SimpleExpansion
{
	public Testing()
	{
		super();

		this.addCards(Rarity.SPECIAL, "R&D's Secret Lair");
	}
}
