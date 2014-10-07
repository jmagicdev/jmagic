package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scout the Borders")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ScouttheBorders extends Card
{
	public ScouttheBorders(GameState state)
	{
		super(state);

		// Reveal the top five cards of your library. You may put a creature or
		// land card from among them into your hand. Put the rest into your
		// graveyard.
		this.addEffect(Sifter.start().reveal(5).take(1, HasType.instance(Type.CREATURE, Type.LAND)).dumpToGraveyard().getEventFactory("Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard."));
	}
}
