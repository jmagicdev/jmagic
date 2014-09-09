package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blade-Tribe Berserkers")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class BladeTribeBerserkers extends Card
{
	public static final class BladeTribeBerserkersAbility0 extends EventTriggeredAbility
	{
		public BladeTribeBerserkersAbility0(GameState state)
		{
			super(state, "When Blade-Tribe Berserkers enters the battlefield, if you control three or more artifacts, Blade-Tribe Berserkers gets +3/+3 and gains haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Metalcraft.instance();
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +3, "Blade-Tribe Berserkers gets +3/+3 and gains haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public BladeTribeBerserkers(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Metalcraft \u2014 When Blade-Tribe Berserkers enters the battlefield,
		// if you control three or more artifacts, Blade-Tribe Berserkers gets
		// +3/+3 and gains haste until end of turn.
		this.addAbility(new BladeTribeBerserkersAbility0(state));
	}
}
