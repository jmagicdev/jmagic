package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sheltering Word")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class ShelteringWord extends Card
{
	public ShelteringWord(GameState state)
	{
		super(state);

		// Target creature you control gains hexproof until end of turn. You
		// gain life equal to that creature's toughness. (A creature with
		// hexproof can't be the target of spells or abilities opponents
		// control.)
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Target creature you control gains hexproof until end of turn."));
		this.addEffect(gainLife(You.instance(), ToughnessOf.instance(target), "You gain life equal to that creature's toughness."));
	}
}
