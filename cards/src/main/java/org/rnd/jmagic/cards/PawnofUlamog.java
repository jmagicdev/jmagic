package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pawn of Ulamog")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class PawnofUlamog extends Card
{
	public static final class PawnofUlamogAbility0 extends EventTriggeredAbility
	{
		public PawnofUlamogAbility0(GameState state)
		{
			super(state, "Whenever Pawn of Ulamog or another nontoken creature you control dies, you may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisDies());

			SetGenerator yourOtherCreatures = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(yourOtherCreatures));

			EventFactory token = createEldraziSpawnTokens(numberGenerator(1), "Put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addEffect(youMay(token, "You may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public PawnofUlamog(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Pawn of Ulamog or another nontoken creature you control is
		// put into a graveyard from the battlefield, you may put a 0/1
		// colorless Eldrazi Spawn creature token onto the battlefield. It has
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new PawnofUlamogAbility0(state));
	}
}
