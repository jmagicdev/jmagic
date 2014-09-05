package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Swift Justice")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SwiftJustice extends Card
{
	public SwiftJustice(GameState state)
	{
		super(state);

		// Until end of turn, target creature gets +1/+0 and gains first strike
		// and lifelink.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +0, "Until end of turn, target creature gets +1/+0 and gains first strike and lifelink.", org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
	}
}
