package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Awakening Zone")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class AwakeningZone extends Card
{
	public static final class SpawnSpawn extends EventTriggeredAbility
	{
		public SpawnSpawn(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory token = createEldraziSpawnTokens(numberGenerator(1), "Put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addEffect(youMay(token, "You may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public AwakeningZone(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may put a 0/1 colorless Eldrazi
		// Spawn creature token onto the battlefield. It has
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new SpawnSpawn(state));
	}
}
