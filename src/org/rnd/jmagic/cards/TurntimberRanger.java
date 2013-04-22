package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turntimber Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT, SubType.ALLY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class TurntimberRanger extends Card
{
	public static final class AllyWolves extends EventTriggeredAbility
	{
		public AllyWolves(GameState state)
		{
			super(state, "Whenever Turntimber Ranger or another Ally enters the battlefield under your control, you may put a 2/2 green Wolf creature token onto the battlefield. If you do, put a +1/+1 counter on Turntimber Ranger.");

			this.addPattern(allyTrigger());

			// Whenever Turntimber Ranger or another Ally enters the battlefield
			// under your control, you may put a 2/2 green Wolf creature token
			// onto the battlefield. If you do, put a +1/+1 counter on
			// Turntimber Ranger.
			CreateTokensFactory wolf = new CreateTokensFactory(1, 2, 2, "Put a 2/2 green Wolf creature token onto the battlefield.");
			wolf.setColors(Color.GREEN);
			wolf.setSubTypes(SubType.WOLF);

			EventFactory youMayWolf = youMay(wolf.getEventFactory(), "You may put a 2/2 green Wolf creature token onto the battlefield.");
			EventFactory counter = putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Turntimber Ranger");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put a 2/2 green Wolf creature token onto the battlefield. If you do, put a +1/+1 counter on Turntimber Ranger.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMayWolf));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(counter));
			this.addEffect(effect);
		}
	}

	public TurntimberRanger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new AllyWolves(state));
	}
}
