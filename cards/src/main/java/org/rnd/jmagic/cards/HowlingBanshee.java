package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Howling Banshee")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class HowlingBanshee extends Card
{
	public static final class Howl extends EventTriggeredAbility
	{
		public Howl(GameState state)
		{
			super(state, "When Howling Banshee enters the battlefield, each player loses 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(loseLife(Players.instance(), 3, "Each player loses 3 life."));
		}
	}

	public HowlingBanshee(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Howl(state));
	}
}
