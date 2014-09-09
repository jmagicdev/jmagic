package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Supreme Verdict")
@Types({Type.SORCERY})
@ManaCost("1WWU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SupremeVerdict extends Card
{
	public SupremeVerdict(GameState state)
	{
		super(state);

		// Supreme Verdict can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Destroy all creatures.
		this.addEffect(destroy(CreaturePermanents.instance(), "Destroy all creatures."));
	}
}
