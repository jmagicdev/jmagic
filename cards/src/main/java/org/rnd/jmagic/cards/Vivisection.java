package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vivisection")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Vivisection extends Card
{
	public Vivisection(GameState state)
	{
		super(state);

		// As an additional cost to cast Vivisection, sacrifice a creature.
		this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.CREATURE), "sacrifice a creature"));

		// Draw three cards.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
