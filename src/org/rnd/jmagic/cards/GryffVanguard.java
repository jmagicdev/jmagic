package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Gryff Vanguard")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GryffVanguard extends Card
{
	public static final class GryffVanguardAbility1 extends EventTriggeredAbility
	{
		public GryffVanguardAbility1(GameState state)
		{
			super(state, "When Gryff Vanguard enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public GryffVanguard(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Gryff Vanguard enters the battlefield, draw a card.
		this.addAbility(new GryffVanguardAbility1(state));
	}
}
