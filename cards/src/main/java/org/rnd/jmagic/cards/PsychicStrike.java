package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Psychic Strike")
@Types({Type.INSTANT})
@ManaCost("1UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class PsychicStrike extends Card
{
	public PsychicStrike(GameState state)
	{
		super(state);

		// Counter target spell. Its controller puts the top two cards of his or
		// her library into his or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));
		this.addEffect(millCards(ControllerOf.instance(target), 2, "Its controller puts the top two cards of his or her library into his or her graveyard."));
	}
}
