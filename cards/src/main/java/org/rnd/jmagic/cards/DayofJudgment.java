package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Day of Judgment")
@Types({Type.SORCERY})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class DayofJudgment extends Card
{
	public DayofJudgment(GameState state)
	{
		super(state);

		// Destroy all creatures.
		this.addEffect(destroy(CreaturePermanents.instance(), "Destroy all creatures."));
	}
}
