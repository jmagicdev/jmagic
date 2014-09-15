package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whelming Wave")
@Types({Type.SORCERY})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class WhelmingWave extends Card
{
	public WhelmingWave(GameState state)
	{
		super(state);

		// Return all creatures to their owners' hands except for Krakens,
		// Leviathans, Octopuses, and Serpents.
		SetGenerator excepted = HasSubType.instance(SubType.KRAKEN, SubType.LEVIATHAN, SubType.OCTOPUS, SubType.SERPENT);
		SetGenerator toBounce = RelativeComplement.instance(CreaturePermanents.instance(), excepted);
		this.addEffect(bounce(toBounce, "Return all creatures to their owners' hands except for Krakens, Leviathans, Octopuses, and Serpents."));
	}
}
