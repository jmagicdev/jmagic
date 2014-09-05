package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Essence Scatter")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class EssenceScatter extends Card
{
	public EssenceScatter(GameState state)
	{
		super(state);

		// Counter target creature spell.
		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(creatureSpells, "target creature spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target creature spell."));
	}
}
