package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Human Frailty")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class HumanFrailty extends Card
{
	public HumanFrailty(GameState state)
	{
		super(state);

		// Destroy target Human creature.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasSubType.instance(SubType.HUMAN), CreaturePermanents.instance()), "target Human creature"));
		this.addEffect(destroy(target, "Destroy target Human creature."));
	}
}
