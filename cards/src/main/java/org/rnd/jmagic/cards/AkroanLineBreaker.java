package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Akroan Line Breaker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class AkroanLineBreaker extends Card
{
	public static final class AkroanLineBreakerAbility0 extends EventTriggeredAbility
	{
		public AkroanLineBreakerAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Akroan Line Breaker, Akroan Line Breaker gets +2/+0 and gains intimidate until end of turn.");
			this.addPattern(heroic());
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Akroan Line Breaker gets +2/+0 and gains intimidate until end of turn.", org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public AkroanLineBreaker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Akroan Line
		// Breaker, Akroan Line Breaker gets +2/+0 and gains intimidate until
		// end of turn.
		this.addAbility(new AkroanLineBreakerAbility0(state));
	}
}
