package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("War-Name Aspirant")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class WarNameAspirant extends Card
{
	public static final class WarNameAspirantAbility0 extends StaticAbility
	{
		public WarNameAspirantAbility0(GameState state)
		{
			super(state, "Raid \u2014 War-Name Aspirant enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.canApply = Raid.instance();
		}
	}

	public static final class WarNameAspirantAbility1 extends StaticAbility
	{
		public WarNameAspirantAbility1(GameState state)
		{
			super(state, "War-Name Aspirant can't be blocked by creatures with power 1 or less.");

			SetGenerator restriction = Intersect.instance(Blocking.instance(This.instance()), HasPower.instance(Between.instance(null, 1)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public WarNameAspirant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Raid \u2014 War-Name Aspirant enters the battlefield with a +1/+1
		// counter on it if you attacked with a creature this turn.
		this.addAbility(new WarNameAspirantAbility0(state));

		// War-Name Aspirant can't be blocked by creatures with power 1 or less.
		this.addAbility(new WarNameAspirantAbility1(state));
	}
}
