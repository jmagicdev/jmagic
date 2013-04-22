package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Temporal Mastery")
@Types({Type.SORCERY})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class TemporalMastery extends Card
{
	public TemporalMastery(GameState state)
	{
		super(state);

		// Take an extra turn after this one.
		this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));

		// Exile Temporal Mastery.
		this.addEffect(exile(This.instance(), "Exile Temporal Mastery."));

		// Miracle (1)(U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(1)(U)"));
	}
}
