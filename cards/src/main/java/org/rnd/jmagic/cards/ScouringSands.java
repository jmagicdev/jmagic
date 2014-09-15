package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scouring Sands")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ScouringSands extends Card
{
	public ScouringSands(GameState state)
	{
		super(state);

		// Scouring Sands deals 1 damage to each creature your opponents
		// control.
		SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance());
		this.addEffect(spellDealDamage(1, enemyCreatures, "Scouring Sands deals 1 damage to each creature your opponents control."));

		// Scry 1.
		this.addEffect(scry(1, "Scry 1."));
	}
}
