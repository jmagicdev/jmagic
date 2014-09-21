package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abzan Charm")
@Types({Type.INSTANT})
@ManaCost("WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class AbzanCharm extends Card
{
	public AbzanCharm(GameState state)
	{
		super(state);

		// Choose one --

		// Exile target creature with power 3 or greater.
		{
			SetGenerator target = targetedBy(this.addTarget(1, HasPower.instance(Between.instance(3, null)), "target creature with power 3 or greater"));
			this.addEffect(1, exile(target, "Exile target creature with power 3 or greater."));
		}

		// You draw two cards and you lose 2 life.
		{
			this.addEffect(2, drawCards(You.instance(), 2, "You draw two cards"));
			this.addEffect(2, loseLife(You.instance(), 2, "and you lose 2 life."));
		}

		// Distribute two +1/+1 counters among one or two target creatures.
		{
			SetGenerator target = targetedBy(this.addTarget(3, CreaturePermanents.instance(), "one or two target creatures").setNumber(1, 2));
			this.setDivision(3, Union.instance(numberGenerator(2), Identity.instance("+1/+1 counters")));

			EventFactory effect = new EventFactory(EventType.DISTRIBUTE_COUNTERS, "Distribute two +1/+1 counters among one or two target creatures.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, target);
			effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(3, effect);
		}
	}
}
