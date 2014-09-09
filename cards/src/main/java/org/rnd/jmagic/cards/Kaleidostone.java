package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kaleidostone")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class Kaleidostone extends Card
{
	public static final class KaleidoDraw extends EventTriggeredAbility
	{
		public KaleidoDraw(GameState state)
		{
			super(state, "When Kaleidostone enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawCards(You.instance(), 1, "Draw a card"));
		}
	}

	public static final class KaleidoMana extends ActivatedAbility
	{
		public KaleidoMana(GameState state)
		{
			super(state, "(5), (T), Sacrifice Kaleidostone: Add (W)(U)(B)(R)(G) to your mana pool.");

			this.setManaCost(new ManaPool("5"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Kaleidostone"));

			this.addEffect(addManaToYourManaPoolFromAbility("(W)(U)(B)(R)(G)"));
		}
	}

	public Kaleidostone(GameState state)
	{
		super(state);

		this.addAbility(new KaleidoDraw(state));

		this.addAbility(new KaleidoMana(state));
	}
}
