package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Remove Soul")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class RemoveSoul extends Card
{
	public RemoveSoul(GameState state)
	{
		super(state);

		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(creatureSpells, "target creature spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target creature spell."));
	}
}
