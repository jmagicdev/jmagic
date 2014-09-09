package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Travel Preparations")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class TravelPreparations extends Card
{
	public TravelPreparations(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);

		// Put a +1/+1 counter on each of up to two target creatures.
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put a +1/+1 counter on each of up to two target creatures."));

		// Flashback (1)(W) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(1)(W)"));
	}
}
