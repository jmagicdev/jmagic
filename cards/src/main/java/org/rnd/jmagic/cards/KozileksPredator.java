package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Kozilek's Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI, SubType.DRONE})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KozileksPredator extends Card
{
	public static final class Spawn extends EventTriggeredAbility
	{
		public Spawn(GameState state)
		{
			super(state, "When Kozilek's Predator enters the battlefield, put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(createEldraziSpawnTokens(numberGenerator(2), "Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public KozileksPredator(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Kozilek's Predator enters the battlefield, put two 0/1 colorless
		// Eldrazi Spawn creature tokens onto the battlefield. They have
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new Spawn(state));
	}
}
