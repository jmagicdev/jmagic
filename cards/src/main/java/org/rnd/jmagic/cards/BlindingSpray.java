package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blinding Spray")
@Types({Type.INSTANT})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class BlindingSpray extends Card
{
	public BlindingSpray(GameState state)
	{
		super(state);

		// Creatures your opponents control get -4/-0 until end of turn.
		SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
		this.addEffect(ptChangeUntilEndOfTurn(enemyCreatures, -4, -0, "Creatures your opponents control get -4/-0 until end of turn."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
