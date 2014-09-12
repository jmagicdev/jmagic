package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.BecomesTheTargetPattern;

@Name("Diffusion Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class DiffusionSliver extends Card
{
	public static final class DiffusionSliverAbility0 extends EventTriggeredAbility
	{
		public DiffusionSliverAbility0(GameState state)
		{
			super(state, "Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays (2).");

			this.addPattern(new BecomesTheTargetPattern(SLIVER_CREATURES_YOU_CONTROL, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance())));
			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);

			EventFactory counter = counter(thatSpell, "Counter that spell");

			SetGenerator controller = ControllerOf.instance(thatSpell);
			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay " + "(2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			this.addEffect(unless(controller, counter, pay, "Counter that spell unless its controller pays (2)."));
		}
	}

	public DiffusionSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever a Sliver creature you control becomes the target of a spell
		// or ability an opponent controls, counter that spell or ability unless
		// its controller pays (2).
		this.addAbility(new DiffusionSliverAbility0(state));
	}
}
