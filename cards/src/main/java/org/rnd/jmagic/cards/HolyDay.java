package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Holy Day")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON), @Printings.Printed(ex = Legends.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class HolyDay extends Card
{
	public HolyDay(GameState state)
	{
		super(state);
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(state.game), "Prevent all combat damage that would be dealt this turn."));
	}
}
