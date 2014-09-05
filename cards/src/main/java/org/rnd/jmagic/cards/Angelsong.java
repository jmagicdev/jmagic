package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Angelsong")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Angelsong extends Card
{
	public Angelsong(GameState state)
	{
		super(state);
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
