package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Painful Quandary")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class PainfulQuandary extends Card
{
	public static final class PainfulQuandaryAbility0 extends EventTriggeredAbility
	{
		public PainfulQuandaryAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, that player loses 5 life unless he or she discards a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "That player loses 5 life unless he or she discards a card.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(playerMay(thatPlayer, discardCards(thatPlayer, 1, "He or she discards a card."), "That player may discard a card.")));
			factory.parameters.put(EventType.Parameter.ELSE, Identity.instance(loseLife(thatPlayer, 5, "That player loses 5 life.")));
			this.addEffect(factory);
		}
	}

	public PainfulQuandary(GameState state)
	{
		super(state);

		// Whenever an opponent casts a spell, that player loses 5 life unless
		// he or she discards a card.
		this.addAbility(new PainfulQuandaryAbility0(state));
	}
}
