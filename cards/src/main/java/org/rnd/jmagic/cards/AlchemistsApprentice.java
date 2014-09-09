package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Alchemist's Apprentice")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class AlchemistsApprentice extends Card
{
	public static final class AlchemistsApprenticeAbility0 extends ActivatedAbility
	{
		public AlchemistsApprenticeAbility0(GameState state)
		{
			super(state, "Sacrifice Alchemist's Apprentice: Draw a card.");
			this.addCost(sacrificeThis("Alchemist's Apprentice"));
			this.addEffect(drawACard());
		}
	}

	public AlchemistsApprentice(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Alchemist's Apprentice: Draw a card.
		this.addAbility(new AlchemistsApprenticeAbility0(state));
	}
}
