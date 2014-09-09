package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flashfreeze")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Flashfreeze extends Card
{
	public Flashfreeze(GameState state)
	{
		super(state);

		SetGenerator redOrGreenSpells = Intersect.instance(Spells.instance(), HasColor.instance(Color.RED, Color.GREEN));

		Target target = this.addTarget(redOrGreenSpells, "target red or green spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target red or green spell."));
	}
}
