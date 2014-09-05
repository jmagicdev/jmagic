package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dark Tutelage")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DarkTutelage extends Card
{
	public DarkTutelage(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, reveal the top card of your library
		// and put that card into your hand. You lose life equal to its
		// converted mana cost.
		this.addAbility(new org.rnd.jmagic.abilities.DarkConfidantAbility(state));
	}
}
