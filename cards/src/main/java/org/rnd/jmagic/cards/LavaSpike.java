package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lava Spike")
@Types({Type.SORCERY})
@SubTypes({SubType.ARCANE})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class LavaSpike extends Card
{
	public LavaSpike(GameState state)
	{
		super(state);

		// Lava Spike deals 3 damage to target player.
		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(spellDealDamage(3, targetedBy(target), "Lava Spike deals 3 damage to target player."));
	}
}
