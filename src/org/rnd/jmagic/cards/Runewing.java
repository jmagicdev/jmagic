package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Runewing")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Runewing extends Card
{
	public static final class RunewingAbility1 extends EventTriggeredAbility
	{
		public RunewingAbility1(GameState state)
		{
			super(state, "When Runewing dies, draw a card.");
			this.addPattern(whenThisDies());
			this.addEffect(drawACard());
		}
	}

	public Runewing(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Runewing dies, draw a card.
		this.addAbility(new RunewingAbility1(state));
	}
}
