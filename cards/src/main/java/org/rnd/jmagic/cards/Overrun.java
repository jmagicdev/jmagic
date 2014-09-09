package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Overrun")
@Types({Type.SORCERY})
@ManaCost("2GGG")
@ColorIdentity({Color.GREEN})
public final class Overrun extends Card
{
	public Overrun(GameState state)
	{
		super(state);

		String effectName = "Creatures you control get +3/+3 and gain trample until end of turn.";
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +3, effectName, org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
