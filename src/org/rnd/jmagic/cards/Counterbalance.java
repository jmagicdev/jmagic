package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Counterbalance")
@Types({Type.ENCHANTMENT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Counterbalance extends Card
{
	public static final class CounterbalanceAbility0 extends EventTriggeredAbility
	{
		public CounterbalanceAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatSpell = EventResult.instance(TriggerEvent.instance(This.instance()));

			EventFactory reveal = reveal(TopCards.instance(1, LibraryOf.instance(You.instance())), "Reveal the top card of your library.");

			EventFactory counter = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Counter that spell if it has the same converted mana cost as the revealed card.");
			counter.parameters.put(EventType.Parameter.IF, Intersect.instance(ConvertedManaCostOf.instance(thatSpell), ConvertedManaCostOf.instance(EffectResult.instance(reveal))));
			counter.parameters.put(EventType.Parameter.THEN, Identity.instance(counter(thatSpell, "Counter that spell.")));

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(reveal, "You may reveal the top card of your library.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(counter));
			this.addEffect(factory);
		}
	}

	public Counterbalance(GameState state)
	{
		super(state);

		// Whenever an opponent casts a spell, you may reveal the top card of
		// your library. If you do, counter that spell if it has the same
		// converted mana cost as the revealed card.
		this.addAbility(new CounterbalanceAbility0(state));
	}
}
