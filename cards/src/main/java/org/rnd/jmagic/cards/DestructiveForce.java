package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Destructive Force")
@Types({Type.SORCERY})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class DestructiveForce extends Card
{
	public DestructiveForce(GameState state)
	{
		super(state);

		// Each player sacrifices five lands.
		this.addEffect(sacrifice(Players.instance(), 5, LandPermanents.instance(), "Each player sacrifices five lands."));

		// Destructive Force deals 5 damage to each creature.
		this.addEffect(spellDealDamage(5, CreaturePermanents.instance(), "Destructive Force deals 5 damage to each creature."));
	}
}
