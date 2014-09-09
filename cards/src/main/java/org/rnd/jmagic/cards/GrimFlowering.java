package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Flowering")
@Types({Type.SORCERY})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class GrimFlowering extends Card
{
	public GrimFlowering(GameState state)
	{
		super(state);

		// Draw a card for each creature card in your graveyard.
		SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));
		this.addEffect(drawCards(You.instance(), number, "Draw a card for each creature card in your graveyard."));
	}
}
