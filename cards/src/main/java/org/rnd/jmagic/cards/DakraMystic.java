package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dakra Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class DakraMystic extends Card
{
	public static final class DakraMysticAbility0 extends ActivatedAbility
	{
		public DakraMysticAbility0(GameState state)
		{
			super(state, "(U), (T): Each player reveals the top card of his or her library. You may put the revealed cards into their owners' graveyards. If you don't, each player draws a card.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator top = TopCards.instance(1, LibraryOf.instance(Players.instance()));
			this.addEffect(reveal(top, "Each player reveals the top card of his or her library."));

			EventFactory mill = millCards(Players.instance(), 1, "Put the revealed cards into their owners' graveyards.");
			EventFactory draw = drawCards(Players.instance(), 1, "Each player draws a card.");
			this.addEffect(ifElse(youMay(mill), draw, "You may put the revealed cards into their owners' graveyards. If you don't, each player draws a card."));
		}
	}

	public DakraMystic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (U), (T): Each player reveals the top card of his or her library. You
		// may put the revealed cards into their owners' graveyards. If you
		// don't, each player draws a card.
		this.addAbility(new DakraMysticAbility0(state));
	}
}
