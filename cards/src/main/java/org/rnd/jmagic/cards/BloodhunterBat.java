package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodhunter Bat")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class BloodhunterBat extends Card
{
	public static final class BloodhunterBatAbility1 extends EventTriggeredAbility
	{
		public BloodhunterBatAbility1(GameState state)
		{
			super(state, "When Bloodhunter Bat enters the battlefield, target player loses 2 life and you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 2, "Target player loses 2 life"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public BloodhunterBat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Bloodhunter Bat enters the battlefield, target player loses 2
		// life and you gain 2 life.
		this.addAbility(new BloodhunterBatAbility1(state));
	}
}
