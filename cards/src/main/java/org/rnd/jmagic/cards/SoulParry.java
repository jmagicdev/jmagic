package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Soul Parry")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SoulParry extends Card
{
	public SoulParry(GameState state)
	{
		super(state);

		// Prevent all damage one or two target creatures would deal this turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "one or two target creatures");
		target.setNumber(1, 2);
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventAllFrom(state.game, targetedBy(target), "one or two target creatures"), "Prevent all damage one or two target creatures would deal this turn."));
	}
}
