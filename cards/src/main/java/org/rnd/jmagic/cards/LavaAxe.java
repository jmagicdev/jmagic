package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lava Axe")
@Types({Type.SORCERY})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class LavaAxe extends Card
{
	public LavaAxe(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(spellDealDamage(5, targetedBy(target), "Lava Axe deals 5 damage to target player."));
	}
}
