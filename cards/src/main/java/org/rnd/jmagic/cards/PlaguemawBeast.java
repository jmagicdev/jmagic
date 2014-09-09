package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Plaguemaw Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class PlaguemawBeast extends Card
{
	public static final class PlaguemawBeastAbility0 extends ActivatedAbility
	{
		public PlaguemawBeastAbility0(GameState state)
		{
			super(state, "(T), Sacrifice a creature: Proliferate.");
			this.costsTap = true;
			this.addCost(sacrificeACreature());
			this.addEffect(proliferate());
		}
	}

	public PlaguemawBeast(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// (T), Sacrifice a creature: Proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addAbility(new PlaguemawBeastAbility0(state));
	}
}
