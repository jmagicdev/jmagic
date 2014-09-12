package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Overwhelm")
@Types({Type.SORCERY})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class Overwhelm extends Card
{
	public Overwhelm(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Creatures you control get +3/+3 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +3, "Creatures you control get +3/+3 until end of turn."));
	}
}
