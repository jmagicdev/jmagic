package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Murderous Cut")
@Types({Type.INSTANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class MurderousCut extends Card
{
	public MurderousCut(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Destroy target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));
	}
}
