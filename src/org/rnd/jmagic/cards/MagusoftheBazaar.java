package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magus of the Bazaar")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MagusoftheBazaar extends Card
{
	public static final class DrawDiscard extends ActivatedAbility
	{
		public DrawDiscard(GameState state)
		{
			super(state, "(T): Draw two cards, then discard three cards.");
			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));
			this.addEffect(discardCards(You.instance(), 3, "then discard three cards."));
		}
	}

	public MagusoftheBazaar(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Draw two cards, then discard three cards.
		this.addAbility(new DrawDiscard(state));
	}
}
