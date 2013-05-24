package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx's Revelation")
@Types({Type.INSTANT})
@ManaCost("XWUU")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SphinxsRevelation extends Card
{
	public SphinxsRevelation(GameState state)
	{
		super(state);

		// You gain X life
		this.addEffect(gainLife(You.instance(), ValueOfX.instance(This.instance()), "You gain X life"));

		// and draw X cards.
		this.addEffect(drawCards(You.instance(), ValueOfX.instance(This.instance()), "and draw X cards."));
	}
}
