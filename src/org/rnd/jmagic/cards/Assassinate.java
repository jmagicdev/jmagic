package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Assassinate")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Assassinate extends Card
{
	public Assassinate(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Tapped.instance(), HasType.instance(Type.CREATURE)), "target tapped creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target tapped creature."));
	}
}
