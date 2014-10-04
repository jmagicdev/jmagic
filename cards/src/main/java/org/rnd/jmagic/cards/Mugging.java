package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mugging")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Mugging extends Card
{
	public Mugging(GameState state)
	{
		super(state);

		// Mugging deals 2 damage to target creature. That creature can't block
		// this turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(2, target, "Mugging deals 2 damage to target creature."));
		this.addEffect(cantBlockThisTurn(target, "That creature can't block this turn."));
	}
}
