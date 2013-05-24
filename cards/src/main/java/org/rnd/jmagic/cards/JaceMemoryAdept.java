package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jace, Memory Adept")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.JACE})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class JaceMemoryAdept extends Card
{
	public static final class JaceMemoryAdeptAbility0 extends LoyaltyAbility
	{
		public JaceMemoryAdeptAbility0(GameState state)
		{
			super(state, +1, "Draw a card. Target player puts the top card of his or her library into his or her graveyard.");
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(drawACard());
			this.addEffect(millCards(target, 1, "Target player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public static final class JaceMemoryAdeptAbility1 extends LoyaltyAbility
	{
		public JaceMemoryAdeptAbility1(GameState state)
		{
			super(state, 0, "Target player puts the top ten cards of his or her library into his or her graveyard.");
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 10, "Target player puts the top ten cards of his or her library into his or her graveyard."));
		}
	}

	public static final class JaceMemoryAdeptAbility2 extends LoyaltyAbility
	{
		public JaceMemoryAdeptAbility2(GameState state)
		{
			super(state, -7, "Any number of target players each draw twenty cards.");
			Target target = this.addTarget(Players.instance(), "any number of target players");
			target.setNumber(0, null);
			this.addEffect(drawCards(targetedBy(target), 20, "Any number of target players each draw twenty cards."));
		}
	}

	public JaceMemoryAdept(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Draw a card. Target player puts the top card of his or her
		// library into his or her graveyard.
		this.addAbility(new JaceMemoryAdeptAbility0(state));

		// 0: Target player puts the top ten cards of his or her library into
		// his or her graveyard.
		this.addAbility(new JaceMemoryAdeptAbility1(state));

		// -7: Any number of target players each draw twenty cards.
		this.addAbility(new JaceMemoryAdeptAbility2(state));
	}
}
