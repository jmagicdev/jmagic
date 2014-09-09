package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Staff of Nin")
@Types({Type.ARTIFACT})
@ManaCost("6")
@ColorIdentity({})
public final class StaffofNin extends Card
{
	public static final class StaffofNinAbility0 extends EventTriggeredAbility
	{
		public StaffofNinAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, draw a card.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(drawACard());
		}
	}

	public static final class StaffofNinAbility1 extends ActivatedAbility
	{
		public StaffofNinAbility1(GameState state)
		{
			super(state, "(T): Staff of Nin deals 1 damage to target creature or player.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Staff of Nin deals 1 damage to target creature or player."));
		}
	}

	public StaffofNin(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, draw a card.
		this.addAbility(new StaffofNinAbility0(state));

		// (T): Staff of Nin deals 1 damage to target creature or player.
		this.addAbility(new StaffofNinAbility1(state));
	}
}
