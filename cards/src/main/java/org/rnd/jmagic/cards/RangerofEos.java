package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ranger of Eos")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class RangerofEos extends Card
{
	public static final class BringSomeFriends extends EventTriggeredAbility
	{
		public BringSomeFriends(GameState state)
		{
			super(state, "When Ranger of Eos enters the battlefield, you may search your library for up to two creature cards with converted mana cost 1 or less, reveal them, and put them into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator littleGuys = Intersect.instance(HasType.instance(Type.CREATURE), HasConvertedManaCost.instance(Between.instance(null, 1)));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two creature cards with converted mana cost 1 or less, reveal them, and put them into your hand. If you do, shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(littleGuys));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			this.addEffect(youMay(search, "You may search your library for up to two creature cards with converted mana cost 1 or less, reveal them, and put them into your hand. If you do, shuffle your library."));
		}
	}

	public RangerofEos(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Ranger of Eos enters the battlefield, you may search your
		// library for up to two creature cards with converted mana cost 1 or
		// less, reveal them, and put them into your hand. If you do, shuffle
		// your library.
		this.addAbility(new BringSomeFriends(state));
	}
}
