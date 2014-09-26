package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Nav Squad Commandos")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class NavSquadCommandos extends Card
{
	public static final class NavSquadCommandosAbility0 extends EventTriggeredAbility
	{
		public NavSquadCommandosAbility0(GameState state)
		{
			super(state, "Battalion \u2014 Whenever Nav Squad Commandos and at least two other creatures attack, Nav Squad Commandos gets +1/+1 until end of turn. Untap it.");
			this.addPattern(battalion());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Nav Squad Commandos gets +1/+1 until end of turn."));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap it."));
		}
	}

	public NavSquadCommandos(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Battalion \u2014 Whenever Nav Squad Commandos and at least two other
		// creatures attack, Nav Squad Commandos gets +1/+1 until end of turn.
		// Untap it.
		this.addAbility(new NavSquadCommandosAbility0(state));
	}
}
