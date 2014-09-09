package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Final Judgment")
@Types({Type.SORCERY})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class FinalJudgment extends Card
{
	public FinalJudgment(GameState state)
	{
		super(state);

		this.addEffect(exile(CreaturePermanents.instance(), "Exile all creatures."));
	}
}
