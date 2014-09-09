package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Grapeshot")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Grapeshot extends Card
{
	public Grapeshot(GameState state)
	{
		super(state);

		// Grapeshot deals 1 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(1, target, "Grapeshot deals 1 damage to target creature or player."));

		// Storm (When you cast this spell, copy it for each spell cast before
		// it this turn. You may choose new targets for the copies.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}
