package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodfire Mentor")
@Types({Type.CREATURE})
@SubTypes({SubType.EFREET, SubType.SHAMAN})
@ManaCost("2R")
@ColorIdentity({Color.BLUE, Color.RED})
public final class BloodfireMentor extends Card
{
	public static final class BloodfireMentorAbility0 extends ActivatedAbility
	{
		public BloodfireMentorAbility0(GameState state)
		{
			super(state, "(2)(U), (T): Draw a card, then discard a card.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;
			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public BloodfireMentor(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// (2)(U), (T): Draw a card, then discard a card.
		this.addAbility(new BloodfireMentorAbility0(state));
	}
}
