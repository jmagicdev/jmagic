package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Evermind")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@Printings({@Printings.Printed(ex = SaviorsOfKamigawa.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Evermind extends Card
{
	public Evermind(GameState state)
	{
		super(state);

		this.setColorIndicator(Color.BLUE);

		// Draw a card.
		this.addEffect(drawACard());

		// Splice onto Arcane (1)(U)
		this.addAbility(org.rnd.jmagic.abilities.keywords.Splice.ontoArcane(state, "(1)(U)"));
	}
}
