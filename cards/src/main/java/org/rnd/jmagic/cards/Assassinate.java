package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Assassinate")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
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
