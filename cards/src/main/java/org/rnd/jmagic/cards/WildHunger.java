package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wild Hunger")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class WildHunger extends Card
{
	public WildHunger(GameState state)
	{
		super(state);

		// Target creature gets +3/+1 and gains trample until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +3, +1, "Target creature gets +3/+1 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));

		// Flashback (3)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(R)"));
	}
}
