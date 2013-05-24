package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auger Spree")
@Types({Type.INSTANT})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class AugerSpree extends Card
{
	public AugerSpree(GameState state)
	{
		super(state);

		// Target creature gets +4/-4 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, +4, -4, "Target creature gets +4/-4 until end of turn."));
	}
}
