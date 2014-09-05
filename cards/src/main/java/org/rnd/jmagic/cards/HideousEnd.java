package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hideous End")
@Types({Type.INSTANT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HideousEnd extends Card
{
	public HideousEnd(GameState state)
	{
		super(state);

		// Destroy target nonblack creature. Its controller loses 2 life.
		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "target nonblack creature");

		this.addEffect(destroy(targetedBy(target), "Destroy target nonblack creature."));
		this.addEffect(loseLife(ControllerOf.instance(targetedBy(target)), 2, "Its controller loses 2 life."));
	}
}
