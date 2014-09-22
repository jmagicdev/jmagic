package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dead Drop")
@Types({Type.SORCERY})
@ManaCost("9B")
@ColorIdentity({Color.BLACK})
public final class DeadDrop extends Card
{
	public DeadDrop(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Target player sacrifices two creatures.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(sacrifice(target, 2, CreaturePermanents.instance(), "Target player sacrifices two creatures."));
	}
}
