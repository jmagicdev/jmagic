package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Isolation Cell")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class IsolationCell extends Card
{
	public static final class IsolationCellAbility0 extends EventTriggeredAbility
	{
		public IsolationCellAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a creature spell, that player loses 2 life unless he or she pays (2).");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE)));
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			EventFactory loseLife = loseLife(thatPlayer, 2, "That player loses 2 life.");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			pay.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

			this.addEffect(unless(thatPlayer, loseLife, pay, "That player loses 2 life unless he or she pays (2)."));
		}
	}

	public IsolationCell(GameState state)
	{
		super(state);

		// Whenever an opponent casts a creature spell, that player loses 2 life
		// unless he or she pays (2).
		this.addAbility(new IsolationCellAbility0(state));
	}
}
