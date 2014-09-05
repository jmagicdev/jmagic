package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dread Drone")
@Types({Type.CREATURE})
@SubTypes({SubType.DRONE, SubType.ELDRAZI})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DreadDrone extends Card
{
	public static final class DreadSpawn extends EventTriggeredAbility
	{
		public DreadSpawn(GameState state)
		{
			super(state, "When Dread Drone enters the battlefield, put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(createEldraziSpawnTokens(numberGenerator(2), "Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public DreadDrone(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// When Dread Drone enters the battlefield, put two 0/1 colorless
		// Eldrazi Spawn creature tokens onto the battlefield. They have
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new DreadSpawn(state));
	}
}
