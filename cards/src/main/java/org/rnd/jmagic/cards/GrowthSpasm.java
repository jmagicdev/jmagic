package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Growth Spasm")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GrowthSpasm extends Card
{
	public GrowthSpasm(GameState state)
	{
		super(state);

		this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		this.addEffect(createEldraziSpawnTokens(numberGenerator(1), "Put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add (1) to your mana pool.\""));
	}
}
