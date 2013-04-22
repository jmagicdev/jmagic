package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chain Reaction")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ChainReaction extends Card
{
	public ChainReaction(GameState state)
	{
		super(state);

		// Chain Reaction deals X damage to each creature, where X is the number
		// of creatures on the battlefield.
		this.addEffect(spellDealDamage(Count.instance(CreaturePermanents.instance()), CreaturePermanents.instance(), "Chain Reaction deals X damage to each creature, where X is the number of creatures on the battlefield."));
	}
}
