package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undying Evil")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class UndyingEvil extends Card
{
	public UndyingEvil(GameState state)
	{
		super(state);

		// Target creature gains undying until end of turn. (When it dies, if it
		// had no +1/+1 counters on it, return it to the battlefield under its
		// owner's control with a +1/+1 counter on it.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Undying.class, "Target creature gains undying until end of turn."));
	}
}
