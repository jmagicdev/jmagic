package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Teleportal")
@Types({Type.SORCERY})
@ManaCost("UR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Teleportal extends Card
{
	public Teleportal(GameState state)
	{
		super(state);

		// Target creature you control gets +1/+0 until end of turn and is
		// unblockable this turn.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(ptChangeUntilEndOfTurn(target, +1, +0, "Target creature you control gets +1/+0 until end of turn"));
		this.addEffect(createFloatingEffect("and is unblockable this turn.", unblockable(target)));

		// Overload (3)(U)(R) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(3)(U)(R)"));
	}
}
