package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Holy Day")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class HolyDay extends Card
{
	public HolyDay(GameState state)
	{
		super(state);
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(state.game), "Prevent all combat damage that would be dealt this turn."));
	}
}
