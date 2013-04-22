package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shrivel")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Shrivel extends Card
{
	public Shrivel(GameState state)
	{
		super(state);

		// All creatures get -1/-1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), -1, -1, "All creatures get -1/-1 until end of turn."));
	}
}
