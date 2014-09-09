package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Fiery Temper")
@Types({Type.INSTANT})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class FieryTemper extends Card
{
	public FieryTemper(GameState state)
	{
		super(state);

		// Fiery Temper deals 3 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(3, target, "Fiery Temper deals 3 damage to target creature or player."));

		// Madness (R) (If you discard this card, you may cast it for its
		// madness cost instead of putting it into your graveyard.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Madness(state, "(R)"));
	}
}
