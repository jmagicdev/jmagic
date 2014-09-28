package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quiet Contemplation")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class QuietContemplation extends Card
{
	public static final class QuietContemplationAbility0 extends EventTriggeredAbility
	{
		public QuietContemplationAbility0(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, you may pay (1). If you do, tap target creature an opponent controls and it doesn't untap during its controller's next untap step.");

			this.addPattern(whenYouCastANoncreatureSpell());

			EventFactory mayPay = youMayPay("(1)");

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(enemyCreatures, "target creature an opponent controls"));

			EventFactory tap = new EventFactory(EventType.TAP_HARD, "Tap target creature an opponent controls and it doesn't untap during its controller's next untap step.");
			tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tap.parameters.put(EventType.Parameter.OBJECT, target);

			this.addEffect(ifThen(mayPay, tap, "You may pay (1). If you do, tap target creature an opponent controls and it doesn't untap during its controller's next untap step."));
		}
	}

	public QuietContemplation(GameState state)
	{
		super(state);

		// Whenever you cast a noncreature spell, you may pay (1). If you do,
		// tap target creature an opponent controls and it doesn't untap during
		// its controller's next untap step.
		this.addAbility(new QuietContemplationAbility0(state));
	}
}
