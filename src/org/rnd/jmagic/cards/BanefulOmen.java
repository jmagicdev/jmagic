package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Baneful Omen")
@Types({Type.ENCHANTMENT})
@ManaCost("4BBB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BanefulOmen extends Card
{
	public static final class Omen extends EventTriggeredAbility
	{
		public Omen(GameState state)
		{
			super(state, "At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.");

			this.addPattern(atTheBeginningOfYourEndStep());

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top card of your library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(reveal, "You may reveal the top card of your library.")));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(loseLife(OpponentsOf.instance(You.instance()), ConvertedManaCostOf.instance(topCard), "Each opponent loses life equal to that card's converted mana cost.")));
			this.addEffect(ifFactory);
		}
	}

	public BanefulOmen(GameState state)
	{
		super(state);

		// At the beginning of your end step, you may reveal the top card of
		// your library. If you do, each opponent loses life equal to that
		// card's converted mana cost.
		this.addAbility(new Omen(state));
	}
}
