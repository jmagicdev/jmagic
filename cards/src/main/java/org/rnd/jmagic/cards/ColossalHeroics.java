package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Colossal Heroics")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ColossalHeroics extends Card
{
	public ColossalHeroics(GameState state)
	{
		super(state);

		// Strive \u2014 Colossal Heroics costs (1)(G) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)(G)"));

		// Any number of target creatures each get +2/+2 until end of turn.
		// Untap those creatures.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(ptChangeUntilEndOfTurn(target, +2, +2, "Any number of target creatures each get +2/+2 until end of turn."));
		this.addEffect(untap(target, "Untap those creatures."));
	}
}
