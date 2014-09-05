package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Time Sieve")
@Types({Type.ARTIFACT})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class TimeSieve extends Card
{
	public static final class TimeSieveAbility0 extends ActivatedAbility
	{
		public TimeSieveAbility0(GameState state)
		{
			super(state, "(T), Sacrifice five artifacts: Take an extra turn after this one.");
			this.costsTap = true;

			this.addCost(sacrifice(You.instance(), 5, ArtifactPermanents.instance(), "Sacrifice five artifacts"));
			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public TimeSieve(GameState state)
	{
		super(state);

		// (T), Sacrifice five artifacts: Take an extra turn after this one.
		this.addAbility(new TimeSieveAbility0(state));
	}
}
