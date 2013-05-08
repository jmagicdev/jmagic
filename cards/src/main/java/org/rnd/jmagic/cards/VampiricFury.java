package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vampiric Fury")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VampiricFury extends Card
{
	public VampiricFury(GameState state)
	{
		super(state);

		// Vampire creatures you control get +2/+0 and gain first strike until
		// end of turn.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(Intersect.instance(HasSubType.instance(SubType.VAMPIRE), CREATURES_YOU_CONTROL), +2, +0, "Vampire creatures you control get +2/+0 and gain first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
