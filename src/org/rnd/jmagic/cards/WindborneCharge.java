package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Windborne Charge")
@Types({Type.SORCERY})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class WindborneCharge extends Card
{
	public WindborneCharge(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_YOU_CONTROL, "two target creatures you control");
		target.setNumber(2, 2);

		// each get +2/+2 and gain flying until end of turn.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +2, +2, "Two target creatures you control each get +2/+2 and gain flying until end of turn.", org.rnd.jmagic.abilities.keywords.Flying.class));
	}
}
