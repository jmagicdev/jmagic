package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Desperate Stand")
@Types({Type.SORCERY})
@ManaCost("RW")
@ColorIdentity({Color.RED, Color.WHITE})
public final class DesperateStand extends Card
{
	public DesperateStand(GameState state)
	{
		super(state);

		// Strive \u2014 Desperate Stand costs (R)(W) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(R)(W)"));

		// Any number of target creatures each get +2/+0 and gain first strike
		// and vigilance until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Any number of target creatures each get +2/+0 and gain first strike and vigilance until end of turn.",//
				org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
				org.rnd.jmagic.abilities.keywords.Vigilance.class));
	}
}
