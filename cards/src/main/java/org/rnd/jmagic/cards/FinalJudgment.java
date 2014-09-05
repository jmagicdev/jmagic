package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Final Judgment")
@Types({Type.SORCERY})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class FinalJudgment extends Card
{
	public FinalJudgment(GameState state)
	{
		super(state);

		this.addEffect(exile(CreaturePermanents.instance(), "Exile all creatures."));
	}
}
