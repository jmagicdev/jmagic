package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gigadrowse")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Gigadrowse extends Card
{
	public Gigadrowse(GameState state)
	{
		super(state);
		Target target = this.addTarget(org.rnd.jmagic.engine.generators.Permanents.instance(), "target permanent");
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Replicate(state, "(U)"));
		this.addEffect(org.rnd.jmagic.Convenience.tap(targetedBy(target), "Tap target permanent."));
	}
}
