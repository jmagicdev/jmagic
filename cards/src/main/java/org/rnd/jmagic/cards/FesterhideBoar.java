package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Festerhide Boar")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class FesterhideBoar extends Card
{
	public static final class MorbidCounters extends StaticAbility
	{
		public MorbidCounters(GameState state)
		{
			super(state, "Festerhide Boar enters the battlefield with two +1/+1 counters on it if a creature died this turn.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			state.ensureTracker(new Morbid.Tracker());
			this.canApply = Morbid.instance();
		}
	}

	public FesterhideBoar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new MorbidCounters(state));
	}
}
