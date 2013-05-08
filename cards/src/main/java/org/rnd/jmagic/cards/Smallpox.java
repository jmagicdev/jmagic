package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smallpox")
@Types({Type.SORCERY})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Smallpox extends Card
{
	public Smallpox(GameState state)
	{
		super(state);

		// Each player loses 1 life, discards a card, sacrifices a creature,
		// then sacrifices a land.
		this.addEffect(loseLife(Players.instance(), 1, "Each player loses 1 life,"));
		this.addEffect(discardCards(Players.instance(), 1, "discards a card,"));
		this.addEffect(sacrifice(Players.instance(), 1, CreaturePermanents.instance(), "sacrifices a creature,"));
		this.addEffect(sacrifice(Players.instance(), 1, LandPermanents.instance(), "then sacrifices a land."));
	}
}
