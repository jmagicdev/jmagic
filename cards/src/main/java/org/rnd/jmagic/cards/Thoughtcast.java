package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thoughtcast")
@Types({Type.SORCERY})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class Thoughtcast extends Card
{
	public Thoughtcast(GameState state)
	{
		super(state);

		// Affinity for artifacts
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Affinity.ForArtifacts(state));

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
