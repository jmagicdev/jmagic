package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Solidarity of Heroes")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class SolidarityofHeroes extends Card
{
	public SolidarityofHeroes(GameState state)
	{
		super(state);

		// Strive \u2014 Solidarity of Heroes costs (1)(G) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)(G)"));

		// Choose any number of target creatures. Double the number of +1/+1
		// counters on each of them.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));

		DynamicEvaluation eachTarget = DynamicEvaluation.instance();

		SetGenerator amount = Count.instance(CountersOn.instance(eachTarget, Counter.CounterType.PLUS_ONE_PLUS_ONE));
		EventFactory doubleEach = putCounters(amount, Counter.CounterType.PLUS_ONE_PLUS_ONE, eachTarget, "Double the number of +1/+1 counters on that creature.");

		EventFactory doubleAll = new EventFactory(FOR_EACH, "Choose any number of target creatures. Double the number of +1/+1 counters on each of them.");
		doubleAll.parameters.put(EventType.Parameter.OBJECT, target);
		doubleAll.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachTarget));
		doubleAll.parameters.put(EventType.Parameter.EFFECT, Identity.instance(doubleEach));
		this.addEffect(doubleAll);
	}
}
