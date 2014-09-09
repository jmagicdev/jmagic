package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mulldrifter")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class Mulldrifter extends Card
{
	public static final class MulldrifterAbility1 extends EventTriggeredAbility
	{
		public MulldrifterAbility1(GameState state)
		{
			super(state, "When Mulldrifter enters the battlefield, draw two cards.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public Mulldrifter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Mulldrifter enters the battlefield, draw two cards.
		this.addAbility(new MulldrifterAbility1(state));

		// Evoke (2)(U) (You may cast this spell for its evoke cost. If you do,
		// it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(2)(U)"));
	}
}
