package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Necrobite")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Necrobite extends Card
{
	public Necrobite(GameState state)
	{
		super(state);

		// Target creature gains deathtouch until end of turn. Regenerate it.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Target creature gains deathtouch until end of turn."));
		this.addEffect(regenerate(target, "Regenerate it."));
	}
}
