package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Manalith")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Manalith extends Card
{
	public Manalith(GameState state)
	{
		super(state);

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
