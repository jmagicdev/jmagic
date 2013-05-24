package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bioshift")
@Types({Type.INSTANT})
@ManaCost("(G/U)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class Bioshift extends Card
{
	public Bioshift(GameState state)
	{
		super(state);

		// Move any number of +1/+1 counters from target creature onto another
		// target creature with the same controller.
		Target source = this.addTarget(CreaturePermanents.instance(), "target creature to move a +1/+1 counter off of");
		source.restrictFromLaterTargets = true;
		SetGenerator firstTarget = targetedBy(source);

		Target destination = this.addTarget(Intersect.instance(ControlledBy.instance(ControllerOf.instance(firstTarget)), CreaturePermanents.instance()), "target creature to move the +1/+1 counter onto");
		SetGenerator secondTarget = targetedBy(destination);

		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE_NUMBER, "");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.CHOICE, Between.instance(numberGenerator(0), Count.instance(CountersOn.instance(firstTarget, Counter.CounterType.PLUS_ONE_PLUS_ONE))));
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance("Choose the number of counters to move."));
		this.addEffect(choose);

		EventFactory moveCounters = new EventFactory(EventType.MOVE_COUNTERS, "Move any number of +1/+1 counters from target creature onto another target creature with the same controller");
		moveCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
		moveCounters.parameters.put(EventType.Parameter.FROM, firstTarget);
		moveCounters.parameters.put(EventType.Parameter.TO, secondTarget);
		moveCounters.parameters.put(EventType.Parameter.NUMBER, EffectResult.instance(choose));
		moveCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		this.addEffect(moveCounters);
	}
}
