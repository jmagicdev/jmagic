package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Mizzium Skin")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MizziumSkin extends Card
{
	public MizziumSkin(GameState state)
	{
		super(state);

		// Target creature you control gets +0/+1 and gains hexproof until end
		// of turn.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +0, +1, "Target creature you control gets +0/+1 and gains hexproof until end of turn.", org.rnd.jmagic.abilities.keywords.Hexproof.class));

		// Overload (1)(U) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(1)(U)"));
	}
}
