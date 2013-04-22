package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Frost Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class FrostTitan extends Card
{
	public static final class FrostTitanAbility0 extends EventTriggeredAbility
	{
		public FrostTitanAbility0(GameState state)
		{
			super(state, "Whenever Frost Titan becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays (2).");

			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance())));
			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);

			EventFactory counter = counter(thatSpell, "Counter that spell");

			SetGenerator controller = ControllerOf.instance(thatSpell);
			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay " + "(2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			this.addEffect(unless(controller, counter, pay, "Counter that spell unless its controller pays (2)."));
		}
	}

	public static final class FrostTitanAbility1 extends EventTriggeredAbility
	{
		public FrostTitanAbility1(GameState state)
		{
			super(state, "Whenever Frost Titan enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			EventFactory tapDown = new EventFactory(EventType.TAP_HARD, "Tap target permanent. It doesn't untap during its controller's next untap step.");
			tapDown.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapDown.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(tapDown);
		}
	}

	public FrostTitan(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Whenever Frost Titan becomes the target of a spell or ability an
		// opponent controls, counter that spell or ability unless its
		// controller pays (2).
		this.addAbility(new FrostTitanAbility0(state));

		// Whenever Frost Titan enters the battlefield or attacks, tap target
		// permanent. It doesn't untap during its controller's next untap step.
		this.addAbility(new FrostTitanAbility1(state));
	}
}
