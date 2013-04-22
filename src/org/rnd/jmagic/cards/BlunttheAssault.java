package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blunt the Assault")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BlunttheAssault extends Card
{
	public BlunttheAssault(GameState state)
	{
		super(state);

		// You gain 1 life for each creature on the battlefield.
		this.addEffect(gainLife(You.instance(), Count.instance(CreaturePermanents.instance()), "You gain 1 life for each creature on the battlefield."));

		// Prevent all combat damage that would be dealt this turn.
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));
	}
}
