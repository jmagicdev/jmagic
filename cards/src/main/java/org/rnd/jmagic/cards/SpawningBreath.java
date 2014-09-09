package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Spawning Breath")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class SpawningBreath extends Card
{
	public SpawningBreath(GameState state)
	{
		super(state);

		// Spawning Breath deals 1 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(1, target, "Spawning Breath deals 1 damage to target creature or player."));

		// Put a 0/1 colorless Eldrazi Spawn creature token onto the
		// battlefield. It has
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addEffect(createEldraziSpawnTokens(numberGenerator(1), "Put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\""));
	}
}
