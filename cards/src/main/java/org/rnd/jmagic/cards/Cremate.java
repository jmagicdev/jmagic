package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cremate")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Cremate extends Card
{
	public Cremate(GameState state)
	{
		super(state);

		// Exile target card from a graveyard.
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));
		this.addEffect(exile(target, "Exile target card from a graveyard."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
