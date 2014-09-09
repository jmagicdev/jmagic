package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Essence Feed")
@Types({Type.SORCERY})
@ManaCost("5B")
@ColorIdentity({Color.BLACK})
public final class EssenceFeed extends Card
{
	public EssenceFeed(GameState state)
	{
		super(state);

		// Target player loses 3 life.
		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(loseLife(targetedBy(target), 3, "Target player loses 3 life."));

		// You gain 3 life
		this.addEffect(gainLife(You.instance(), 3, "You gain 3 life"));

		// and put three 0/1 colorless Eldrazi Spawn creature tokens onto the
		// battlefield. They have "Sacrifice this creature: Add (1) to your mana
		// pool."
		this.addEffect(createEldraziSpawnTokens(numberGenerator(3), "and put three 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
	}
}
