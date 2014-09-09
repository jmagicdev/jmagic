package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Fog")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Fog extends Card
{
	public Fog(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt this turn.
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));
	}
}
