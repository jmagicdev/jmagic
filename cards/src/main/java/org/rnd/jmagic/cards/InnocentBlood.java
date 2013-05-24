package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Innocent Blood")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class InnocentBlood extends Card
{
	public InnocentBlood(GameState state)
	{
		super(state);

		// Each player sacrifices a creature.
		this.addEffect(sacrifice(Players.instance(), 1, CreaturePermanents.instance(), "Each player sacrifices a creature."));
	}
}
