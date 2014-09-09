package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Isle of Vesuva")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@ColorIdentity({})
public final class IsleofVesuva extends Card
{
	public static final class TwinSpawn extends EventTriggeredAbility
	{
		public TwinSpawn(GameState state)
		{
			super(state, "Whenever a nontoken creature enters the battlefield, its controller puts a token onto the battlefield that's a copy of that creature.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Intersect.instance(NonToken.instance(), CreaturePermanents.instance()), false));

			SetGenerator it = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator itsController = ControllerOf.instance(it);

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Its controller puts a token onto the battlefield that's a copy of that creature.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, itsController);
			factory.parameters.put(EventType.Parameter.OBJECT, it);
			this.addEffect(factory);

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class EchoingChaos extends EventTriggeredAbility
	{
		public EchoingChaos(GameState state)
		{
			super(state, "Whenever you roll (C), destroy target creature and all other creatures with the same name as that creature.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(destroy(Union.instance(targetedBy(target), Intersect.instance(CreaturePermanents.instance(), HasName.instance(NameOf.instance(targetedBy(target))))), "Destroy target creature and all other creatures with the same name as that creature."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public IsleofVesuva(GameState state)
	{
		super(state);

		this.addAbility(new TwinSpawn(state));

		this.addAbility(new EchoingChaos(state));
	}
}
