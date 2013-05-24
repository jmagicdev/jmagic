package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jace Beleren")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.JACE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class JaceBeleren extends Card
{
	public static final class EachDraws extends LoyaltyAbility
	{
		public EachDraws(GameState state)
		{
			super(state, +2, "Each player draws a card.");
			this.addEffect(drawCards(Players.instance(), 1, "Each player draws a card."));
		}
	}

	public static final class TargetDraws extends LoyaltyAbility
	{
		public TargetDraws(GameState state)
		{
			super(state, -1, "Target player draws a card.");
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(drawCards(targetedBy(target), 1, "Target player draws a card."));
		}
	}

	public static final class MillTwenty extends LoyaltyAbility
	{
		public MillTwenty(GameState state)
		{
			super(state, -10, "Target player puts the top twenty cards of his or her library into his or her graveyard.");
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(millCards(targetedBy(target), 20, "Target player puts the top twenty cards of his or her library into his or her graveyard."));
		}
	}

	public JaceBeleren(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		this.addAbility(new EachDraws(state));
		this.addAbility(new TargetDraws(state));
		this.addAbility(new MillTwenty(state));
	}
}
