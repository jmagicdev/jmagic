package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Goldmeadow")
@Types({Type.PLANE})
@SubTypes({SubType.LORWYN})
@ColorIdentity({})
public final class Goldmeadow extends Card
{
	public static final class MenWhoStareAtLandfall extends EventTriggeredAbility
	{
		public MenWhoStareAtLandfall(GameState state)
		{
			super(state, "Whenever a land enters the battlefield, that land's controller puts three 0/1 white Goat creature tokens onto the battlefield.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.LAND), false));

			CreateTokensFactory token = new CreateTokensFactory(3, 0, 1, "That land's controller puts three 0/1 white Goat creature tokens onto the battlefield.");
			token.setController(ControllerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance()))));
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.GOAT);
			this.addEffect(token.getEventFactory());

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class IfJediKillGoatsDoesThatMakeThisASithAbility extends EventTriggeredAbility
	{
		public IfJediKillGoatsDoesThatMakeThisASithAbility(GameState state)
		{
			super(state, "Whenever you roll (C), put a 0/1 white Goat creature token onto the battlefield.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 1, "Put a 0/1 white Goat creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.GOAT);
			this.addEffect(token.getEventFactory());

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Goldmeadow(GameState state)
	{
		super(state);

		this.addAbility(new MenWhoStareAtLandfall(state));

		this.addAbility(new IfJediKillGoatsDoesThatMakeThisASithAbility(state));
	}
}
