package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lionheart Maverick")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LionheartMaverick extends Card
{
	public static final class Pump extends ActivatedAbility
	{
		public Pump(GameState state)
		{
			super(state, "(4)(W): Lionheart Maverick gets +1/+2 until end of turn.");
			this.setManaCost(new ManaPool("4W"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +2, "Lionheart Maverick gets +1/+2 until end of turn."));
		}
	}

	public LionheartMaverick(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (4)(W): Lionheart Maverick gets +1/+2 until end of turn.
		this.addAbility(new Pump(state));
	}
}
