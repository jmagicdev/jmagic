package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Nest Invader")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI, SubType.DRONE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class NestInvader extends Card
{
	public static final class Spawn extends EventTriggeredAbility
	{
		public Spawn(GameState state)
		{
			super(state, "When Nest Invader enters the battlefield, put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(createEldraziSpawnTokens(numberGenerator(1), "Put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public NestInvader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Nest Invader enters the battlefield, put a 0/1 colorless Eldrazi
		// Spawn creature token onto the battlefield. It has
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new Spawn(state));
	}
}
