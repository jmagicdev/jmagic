package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Priest of Urabrask")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class PriestofUrabrask extends Card
{
	public static final class PriestofUrabraskAbility0 extends EventTriggeredAbility
	{
		public PriestofUrabraskAbility0(GameState state)
		{
			super(state, "When Priest of Urabrask enters the battlefield, add (R)(R)(R) to your mana pool.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(addManaToYourManaPoolFromAbility("(R)(R)(R)"));
		}
	}

	public PriestofUrabrask(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Priest of Urabrask enters the battlefield, add (R)(R)(R) to your
		// mana pool.
		this.addAbility(new PriestofUrabraskAbility0(state));
	}
}
