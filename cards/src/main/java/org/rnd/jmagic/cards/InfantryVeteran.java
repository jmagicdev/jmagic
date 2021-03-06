package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Infantry Veteran")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class InfantryVeteran extends Card
{
	public static final class InfantryVeteranAbility0 extends ActivatedAbility
	{
		public InfantryVeteranAbility0(GameState state)
		{
			super(state, "(T): Target attacking creature gets +1/+1 until end of turn.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Attacking.instance(), "target attacking creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, 1, 1, "Target attacking creature gets +1/+1 until end of turn."));
		}
	}

	public InfantryVeteran(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target attacking creature gets +1/+1 until end of turn.
		this.addAbility(new InfantryVeteranAbility0(state));
	}
}
