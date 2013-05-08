package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Surrakar Marauder")
@Types({Type.CREATURE})
@SubTypes({SubType.SURRAKAR})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SurrakarMarauder extends Card
{
	public static final class LandfallIntimidate extends EventTriggeredAbility
	{
		public LandfallIntimidate(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, Surrakar Marauder gains intimidate until end of turn.");

			this.addPattern(landfall());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Intimidate.class, "Surrakar Marauder gains intimidate until end of turn."));
		}
	}

	public SurrakarMarauder(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Surrakar Marauder gains intimidate until end of turn. (It
		// can't be blocked except by artifact creatures and/or creatures that
		// share a color with it.)
		this.addAbility(new LandfallIntimidate(state));
	}
}
