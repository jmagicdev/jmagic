package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gravepurge")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Gravepurge extends Card
{
	public Gravepurge(GameState state)
	{
		super(state);

		// Put any number of target creature cards from your graveyard on top of
		// your library.
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "any number of target creature cards in your graveyard");
		target.setRange(Between.instance(0, null));

		this.addEffect(putOnTopOfLibrary(targetedBy(target), "Put any number of target creature cards from your graveyard on top of your library."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
