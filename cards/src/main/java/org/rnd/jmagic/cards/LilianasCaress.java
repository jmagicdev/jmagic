package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Liliana's Caress")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class LilianasCaress extends Card
{
	public static final class LilianasCaressAbility0 extends EventTriggeredAbility
	{
		public LilianasCaressAbility0(GameState state)
		{
			super(state, "Whenever an opponent discards a card, that player loses 2 life.");

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, new OwnedByPattern(OpponentsOf.instance(You.instance())));
			this.addPattern(discard);

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.CARD));
			this.addEffect(loseLife(thatPlayer, 2, "That player loses 2 life."));
		}
	}

	public LilianasCaress(GameState state)
	{
		super(state);

		// Whenever an opponent discards a card, that player loses 2 life.
		this.addAbility(new LilianasCaressAbility0(state));
	}
}
