package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Warrior's Honor")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Visions.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WarriorsHonor extends Card
{
	public WarriorsHonor(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));
	}
}
