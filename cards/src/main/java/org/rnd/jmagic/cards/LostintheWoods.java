package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lost in the Woods")
@Types({Type.ENCHANTMENT})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class LostintheWoods extends Card
{
	public static final class LostintheWoodsAbility0 extends EventTriggeredAbility
	{
		public LostintheWoodsAbility0(GameState state)
		{
			super(state, "Whenever a creature attacks you or a planeswalker you control, reveal the top card of your library. If it's a Forest card, remove that creature from combat. Then put the revealed card on the bottom of your library.");

			SetGenerator youAndYours = Union.instance(You.instance(), Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(You.instance())));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
			pattern.put(EventType.Parameter.DEFENDER, youAndYours);
			this.addPattern(pattern);

			EventFactory reveal = reveal(TopCards.instance(1, LibraryOf.instance(You.instance())), "Reveal the top card of your library.");
			this.addEffect(reveal);

			SetGenerator revealedCard = EffectResult.instance(reveal);
			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);

			EventFactory remove = new EventFactory(EventType.REMOVE_FROM_COMBAT, "Remove that creature from combat.");
			remove.parameters.put(EventType.Parameter.OBJECT, thatCreature);
			this.addEffect(ifThen(Intersect.instance(revealedCard, HasSubType.instance(SubType.FOREST)), remove, "If it's a Forest card, remove that creature from combat."));

			this.addEffect(putOnBottomOfLibrary(revealedCard, "Then put the revealed card on the bottom of your library."));
		}
	}

	public LostintheWoods(GameState state)
	{
		super(state);

		// Whenever a creature attacks you or a planeswalker you control, reveal
		// the top card of your library. If it's a Forest card, remove that
		// creature from combat. Then put the revealed card on the bottom of
		// your library.
		this.addAbility(new LostintheWoodsAbility0(state));
	}
}
