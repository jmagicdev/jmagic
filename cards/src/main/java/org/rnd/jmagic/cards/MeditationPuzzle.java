package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Meditation Puzzle")
@Types({Type.INSTANT})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class MeditationPuzzle extends Card
{
	public MeditationPuzzle(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// You gain 8 life.
		this.addEffect(gainLife(You.instance(), 8, "You gain 8 life."));
	}
}
