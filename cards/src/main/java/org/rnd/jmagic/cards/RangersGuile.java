package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ranger's Guile")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RangersGuile extends Card
{
	public RangersGuile(GameState state)
	{
		super(state);

		// Target creature you control gets +1/+1 and gains hexproof until end
		// of turn. (It can't be the target of spells or abilities your
		// opponents control.)
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature you control gets +1/+1 and gains hexproof until end of turn.", org.rnd.jmagic.abilities.keywords.Hexproof.class));
	}
}
