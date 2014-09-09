package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Fog Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class FogElemental extends Card
{
	public static final class Disipate extends EventTriggeredAbility
	{
		public Disipate(GameState state)
		{
			super(state, "When Fog Elemental attacks or blocks, sacrifice it at end of combat.");
			this.addPattern(whenThisAttacks());
			this.addPattern(whenThisBlocks());

			SimpleEventPattern endCombatEvent = new SimpleEventPattern(EventType.BEGIN_STEP);
			endCombatEvent.put(EventType.Parameter.STEP, EndOfCombatStepOf.instance(Players.instance()));

			EventType.ParameterMap sacParameters = new EventType.ParameterMap();
			sacParameters.put(EventType.Parameter.CAUSE, This.instance());
			sacParameters.put(EventType.Parameter.PLAYER, You.instance());
			sacParameters.put(EventType.Parameter.PERMANENT, ABILITY_SOURCE_OF_THIS);

			EventType.ParameterMap triggerParameters = new EventType.ParameterMap();
			triggerParameters.put(EventType.Parameter.CAUSE, This.instance());
			triggerParameters.put(EventType.Parameter.EVENT, Identity.instance(endCombatEvent));
			triggerParameters.put(EventType.Parameter.EFFECT, Identity.instance(new EventFactory(EventType.SACRIFICE_PERMANENTS, sacParameters, "Sacrifice it")));
			this.addEffect(new EventFactory(EventType.CREATE_DELAYED_TRIGGER, triggerParameters, "Sacrifice it at end of combat"));
		}
	}

	public FogElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Disipate(state));
	}
}
