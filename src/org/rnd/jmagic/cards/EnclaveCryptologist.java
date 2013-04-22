package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Enclave Cryptologist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class EnclaveCryptologist extends Card
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

	public static final class Draw extends ActivatedAbility
	{
		public Draw(GameState state)
		{
			super(state, "(T): Draw a card.");
			this.costsTap = true;
			this.addEffect(drawACard());
		}
	}

	public EnclaveCryptologist(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Level up (1)(U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(U)"));

		// LEVEL 1-2
		// 0/1
		// (T): Draw a card, then discard a card.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 2, 0, 1, "(T): Draw a card, then discard a card.", DrawDiscard.class));

		// LEVEL 3+
		// 0/1
		// (T): Draw a card.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 3, 0, 1, "(T): Draw a card.", Draw.class));
	}
}
