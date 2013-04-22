package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Psychic Barrier")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PsychicBarrier extends Card
{
	public PsychicBarrier(GameState state)
	{
		super(state);

		// Counter target creature spell. Its controller loses 1 life.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE)), "target creature spell"));
		this.addEffect(counter(target, "Counter target creature spell."));
		this.addEffect(loseLife(ControllerOf.instance(target), 1, "Its controller loses 1 life."));
	}
}
