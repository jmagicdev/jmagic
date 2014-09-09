package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Throne of Geth")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class ThroneofGeth extends Card
{
	public static final class ThroneofGethAbility0 extends ActivatedAbility
	{
		public ThroneofGethAbility0(GameState state)
		{
			super(state, "(T), Sacrifice an artifact: Proliferate.");
			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(proliferate());
		}
	}

	public ThroneofGeth(GameState state)
	{
		super(state);

		// (T), Sacrifice an artifact: Proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addAbility(new ThroneofGethAbility0(state));
	}
}
