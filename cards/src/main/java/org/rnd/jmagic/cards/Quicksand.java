package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quicksand")
@Types({Type.LAND})
@ColorIdentity({})
public final class Quicksand extends Card
{
	public static final class Trap extends ActivatedAbility
	{
		public Trap(GameState state)
		{
			super(state, "(T), Sacrifice Quicksand: Target attacking creature without flying gets -1/-2 until end of turn.");

			this.costsTap = true;

			this.addCost(sacrificeThis("Quicksand"));

			Target target = this.addTarget(RelativeComplement.instance(Attacking.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target attacking creature without flying");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-1), (-2), "Target attacking creature without flying gets -1/-2 until end of turn."));
		}
	}

	public Quicksand(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new Trap(state));
	}
}
