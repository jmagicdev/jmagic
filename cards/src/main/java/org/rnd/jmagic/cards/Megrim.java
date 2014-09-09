package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Megrim")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class Megrim extends Card
{
	public static final class MegrimShock extends EventTriggeredAbility
	{
		public MegrimShock(GameState state)
		{
			super(state, "Whenever an opponent discards a card, Megrim deals 2 damage to that player.");

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, new OwnedByPattern(OpponentsOf.instance(You.instance())));
			this.addPattern(discard);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator discardedCard = EventParameter.instance(triggerEvent, EventType.Parameter.CARD);
			SetGenerator thatPlayer = OwnerOf.instance(discardedCard);

			this.addEffect(permanentDealDamage(2, thatPlayer, "Megrim deals 2 damage to that player."));
		}
	}

	public Megrim(GameState state)
	{
		super(state);

		this.addAbility(new MegrimShock(state));
	}
}
