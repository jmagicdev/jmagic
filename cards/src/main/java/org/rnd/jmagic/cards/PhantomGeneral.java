package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Phantom General")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.SPIRIT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class PhantomGeneral extends Card
{
	public PhantomGeneral(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Creature tokens you control get +1/+1.
		SetGenerator creatureTokens = Intersect.instance(CREATURES_YOU_CONTROL, Tokens.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, creatureTokens, "Creature tokens you control", +1, +1, true));
	}
}
