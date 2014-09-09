package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Promos")
public final class Promos extends SimpleExpansion
{
	public Promos()
	{
		super();

		this.addCards(Rarity.SPECIAL, "Beast of Burden", "Mana Crypt");
	}
}
