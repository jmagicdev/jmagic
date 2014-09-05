package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Smash")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON), @Printings.Printed(ex = Apocalypse.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Smash extends Card
{
	public Smash(GameState state)
	{
		super(state);

		Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
