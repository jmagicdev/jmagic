package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Counsel of the Soratami")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CounseloftheSoratami extends Card
{
	public CounseloftheSoratami(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
