package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("The Fourth Sphere")
@Types({Type.PLANE})
@SubTypes({SubType.PHYREXIA})
@ColorIdentity({})
public final class TheFourthSphere extends Card
{
	public static final class Offering extends EventTriggeredAbility
	{
		public Offering(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a nonblack creature.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(sacrifice(You.instance(), 1, RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "Sacrifice a nonblack creature"));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class SpawnOfChaos extends EventTriggeredAbility
	{
		public SpawnOfChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a 2/2 black Zombie creature token onto the battlefield.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public TheFourthSphere(GameState state)
	{
		super(state);

		this.addAbility(new Offering(state));

		this.addAbility(new SpawnOfChaos(state));
	}
}
