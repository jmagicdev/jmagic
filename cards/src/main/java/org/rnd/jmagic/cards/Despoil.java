package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Despoil")
@Types({Type.SORCERY})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Prophecy.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Despoil extends Card
{
	public Despoil(GameState state)
	{
		super(state);

		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));
		this.addEffect(loseLife(ControllerOf.instance(targetedBy(target)), 2, "Its controller loses 2 life."));
	}
}
