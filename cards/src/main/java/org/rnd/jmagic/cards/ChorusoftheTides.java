package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Chorus of the Tides")
@Types({Type.CREATURE})
@SubTypes({SubType.SIREN})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class ChorusoftheTides extends Card
{
	public static final class ChorusoftheTidesAbility1 extends EventTriggeredAbility
	{
		public ChorusoftheTidesAbility1(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Chorus of the Tides, scry 1.");
			this.addPattern(heroic());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public ChorusoftheTides(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Heroic \u2014 Whenever you cast a spell that targets Chorus of the
		// Tides, scry 1. (To scry 1, look at the top card of your library, then
		// you may put that card on the bottom of your library.)
		this.addAbility(new ChorusoftheTidesAbility1(state));
	}
}
