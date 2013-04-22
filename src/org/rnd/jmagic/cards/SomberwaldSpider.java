package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Somberwald Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SomberwaldSpider extends Card
{
	public static final class SomberwaldSpiderAbility1 extends StaticAbility
	{
		public SomberwaldSpiderAbility1(GameState state)
		{
			super(state, "Somberwald Spider enters the battlefield with two +1/+1 counters on it if a creature died this turn.");

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

			this.canApply = Morbid.instance();

			state.ensureTracker(new Morbid.Tracker());
		}
	}

	public SomberwaldSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Morbid \u2014 Somberwald Spider enters the battlefield with two +1/+1
		// counters on it if a creature died this turn.
		this.addAbility(new SomberwaldSpiderAbility1(state));
	}
}
