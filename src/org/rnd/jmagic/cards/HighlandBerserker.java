package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Highland Berserker")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.ALLY, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class HighlandBerserker extends Card
{
	public static final class AllyStrike extends EventTriggeredAbility
	{
		public AllyStrike(GameState state)
		{
			super(state, "Whenever Highland Berserker or another Ally enters the battlefield under your control, you may have Ally creatures you control gain first strike until end of turn.");

			this.addPattern(allyTrigger());

			EventFactory gainFirstStrike = addAbilityUntilEndOfTurn(ALLY_CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Ally creatures you control gain first strike until end of turn");
			this.addEffect(youMay(gainFirstStrike, "You may have Ally creatures you control gain first strike until end of turn."));
		}
	}

	public HighlandBerserker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new AllyStrike(state));
	}
}
