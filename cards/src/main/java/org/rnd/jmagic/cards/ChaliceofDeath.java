package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chalice of Death")
@Types({Type.ARTIFACT})
@ColorIdentity({})
public final class ChaliceofDeath extends AlternateCard
{
	public static final class ChaliceofDeathAbility0 extends ActivatedAbility
	{
		public ChaliceofDeathAbility0(GameState state)
		{
			super(state, "(T): Target player loses 5 life.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 5, "Target player loses 5 life."));
		}
	}

	public ChaliceofDeath(GameState state)
	{
		super(state);

		// (T): Target player loses 5 life.
		this.addAbility(new ChaliceofDeathAbility0(state));
	}
}
