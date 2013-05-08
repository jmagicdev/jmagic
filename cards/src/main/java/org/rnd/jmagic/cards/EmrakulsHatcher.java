package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Emrakul's Hatcher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI, SubType.DRONE})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class EmrakulsHatcher extends Card
{
	public static final class Hatch extends EventTriggeredAbility
	{
		public Hatch(GameState state)
		{
			super(state, "When Emrakul's Hatcher enters the battlefield, put three 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(createEldraziSpawnTokens(numberGenerator(3), "Put three 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public EmrakulsHatcher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new Hatch(state));
	}
}
