package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Outwit")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Outwit extends Card
{
	public Outwit(GameState state)
	{
		super(state);

		// Counter target spell that targets a player.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasTarget.instance(Players.instance())), "target spell that targets a player"));
		this.addEffect(counter(target, "Counter target spell that targets a player."));
	}
}
