package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thoughtcast")
@Types({Type.SORCERY})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
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
