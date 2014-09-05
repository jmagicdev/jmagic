package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Scab-Clan Charger")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CENTAUR})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ScabClanCharger extends Card
{
	public ScabClanCharger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Bloodrush \u2014 (1)(G), Discard Scab-Clan Charger: Target attacking
		// creature gets +2/+4 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(1)(G)", "Scab-Clan Charger", +2, +4, "Target attacking creature gets +2/+4 until end of turn."));
	}
}
