package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Bloodcrazed Hoplite")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class BloodcrazedHoplite extends Card
{
	public static final class BloodcrazedHopliteAbility0 extends EventTriggeredAbility
	{
		public BloodcrazedHopliteAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Bloodcrazed Hoplite, put a +1/+1 counter on it.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Bloodcrazed Hoplite."));
		}
	}

	public static final class BloodcrazedHopliteAbility1 extends EventTriggeredAbility
	{
		public BloodcrazedHopliteAbility1(GameState state)
		{
			super(state, "Whenever a +1/+1 counter is placed on Bloodcrazed Hoplite, remove a +1/+1 counter from target creature an opponent controls.");

			SimpleEventPattern counterPlaced = new SimpleEventPattern(EventType.PUT_ONE_COUNTER);
			counterPlaced.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			counterPlaced.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(counterPlaced);

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(enemyCreatures, "target creature an opponent controls"));
			this.addEffect(removeCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Remove a +1/+1 counter from target creature an opponent controls."));
		}
	}

	public BloodcrazedHoplite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Bloodcrazed
		// Hoplite, put a +1/+1 counter on it.
		this.addAbility(new BloodcrazedHopliteAbility0(state));

		// Whenever a +1/+1 counter is placed on Bloodcrazed Hoplite, remove a
		// +1/+1 counter from target creature an opponent controls.
		this.addAbility(new BloodcrazedHopliteAbility1(state));
	}
}
