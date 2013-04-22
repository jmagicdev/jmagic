package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sanctum of Serra")
@Types({Type.PLANE})
@SubTypes({SubType.SERRAS_REALM})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SanctumofSerra extends Card
{
	public static final class AWomanScorned extends EventTriggeredAbility
	{
		public AWomanScorned(GameState state)
		{
			super(state, "When you planeswalk away from Sanctum of Serra, destroy all nonland permanents.");

			SimpleEventPattern pattern = new SimpleEventPattern(Planechase.PLANESWALK);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(destroy(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "Destroy all nonland permanents."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class SerrasChaos extends EventTriggeredAbility
	{
		public SerrasChaos(GameState state)
		{
			super(state, "Whenever you roll (C), you may have your life total become 20.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			EventFactory factory = new EventFactory(EventType.SET_LIFE, "Your life total becomes 20.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(20));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(youMay(factory, "You may have your life total become 20."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public SanctumofSerra(GameState state)
	{
		super(state);

		this.addAbility(new AWomanScorned(state));

		this.addAbility(new SerrasChaos(state));
	}
}
