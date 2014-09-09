package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Looter")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.MERFOLK})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class MerfolkLooter extends Card
{
	public static final class DrawDiscard extends ActivatedAbility
	{
		public DrawDiscard(GameState state)
		{
			super(state, "(T): Draw a card, then discard a card.");

			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public MerfolkLooter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new DrawDiscard(state));
	}
}
