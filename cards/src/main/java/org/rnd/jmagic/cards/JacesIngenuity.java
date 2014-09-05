package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jace's Ingenuity")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class JacesIngenuity extends Card
{
	public JacesIngenuity(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
