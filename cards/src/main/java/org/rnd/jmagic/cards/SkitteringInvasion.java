package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Skittering Invasion")
@Types({Type.TRIBAL, Type.SORCERY})
@SubTypes({SubType.ELDRAZI})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SkitteringInvasion extends Card
{
	public SkitteringInvasion(GameState state)
	{
		super(state);

		// Put five 0/1 colorless Eldrazi Spawn creature tokens onto the
		// battlefield. They have
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addEffect(createEldraziSpawnTokens(numberGenerator(5), "Put five 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
	}
}
