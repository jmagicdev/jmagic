package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Wojek Halberdiers")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class WojekHalberdiers extends Card
{
	public static final class WojekHalberdiersAbility0 extends EventTriggeredAbility
	{
		public WojekHalberdiersAbility0(GameState state)
		{
			super(state, "Whenever Wojek Halberdiers and at least two other creatures attack, Wojek Halberdiers gains first strike until end of turn.");
			this.addPattern(battalion());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Wojek Halberdiers gains first strike until end of turn."));
		}
	}

	public WojekHalberdiers(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Battalion \u2014 Whenever Wojek Halberdiers and at least two other
		// creatures attack, Wojek Halberdiers gains first strike until end of
		// turn.
		this.addAbility(new WojekHalberdiersAbility0(state));
	}
}
