package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Inspiration")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter2000.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Visions.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Inspiration extends Card
{
	public Inspiration(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(drawCards(targetedBy(target), 2, "Target player draws two cards."));
	}
}
