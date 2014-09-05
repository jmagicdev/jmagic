package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Day of Judgment")
@Types({Type.SORCERY})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
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
