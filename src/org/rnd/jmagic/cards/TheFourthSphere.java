package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("The Fourth Sphere")
@Types({Type.PLANE})
@SubTypes({SubType.PHYREXIA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
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

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class SpawnOfChaos extends EventTriggeredAbility
	{
		public SpawnOfChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a 2/2 black Zombie creature token onto the battlefield.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public TheFourthSphere(GameState state)
	{
		super(state);

		this.addAbility(new Offering(state));

		this.addAbility(new SpawnOfChaos(state));
	}
}
