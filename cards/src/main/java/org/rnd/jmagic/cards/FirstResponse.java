package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("First Response")
@Types({Type.ENCHANTMENT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class FirstResponse extends Card
{
	public static final class FirstResponseAbility0 extends EventTriggeredAbility
	{
		public FirstResponseAbility0(GameState state)
		{
			super(state, "At the beginning of each upkeep, if you lost life last turn, put a 1/1 white Soldier creature token onto the battlefield.");

			this.addPattern(atTheBeginningOfEachUpkeep());
			this.interveningIf = Intersect.instance(You.instance(), LostLifeLastTurn.instance());

			CreateTokensFactory soldier = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			soldier.setColors(Color.WHITE);
			soldier.setSubTypes(SubType.SOLDIER);
			this.addEffect(soldier.getEventFactory());
		}
	}

	public FirstResponse(GameState state)
	{
		super(state);

		// At the beginning of each upkeep, if you lost life last turn, put a
		// 1/1 white Soldier creature token onto the battlefield.
		this.addAbility(new FirstResponseAbility0(state));
	}
}
