package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Holy Day")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class HolyDay extends Card
{
	public HolyDay(GameState state)
	{
		super(state);
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(state.game), "Prevent all combat damage that would be dealt this turn."));
	}
}
