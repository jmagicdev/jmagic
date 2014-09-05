package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Victorious Destruction")
@Types({Type.SORCERY})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VictoriousDestruction extends Card
{
	public VictoriousDestruction(GameState state)
	{
		super(state);

		// Destroy target artifact or land. Its controller loses 1 life.
		SetGenerator type = Union.instance(ArtifactPermanents.instance(), LandPermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(type, "target artifact or land"));
		this.addEffect(destroy(target, "Destroy target artifact or land."));
		this.addEffect(loseLife(ControllerOf.instance(target), 1, "Its controller loses 1 life."));
	}
}
